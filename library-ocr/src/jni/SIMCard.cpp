#include "simcard_jni.h"

extern int fd;

speed_t getBaudrate(int baudrate)
{
    switch(baudrate) {
    case 9600: return B9600;
    case 19200: return B19200;
    case 38400: return B38400;
    case 57600: return B57600;
    case 115200: return B115200;
    case 230400: return B230400;
    case 460800: return B460800;
    case 500000: return B500000;
    case 576000: return B576000;
    default: return 1;
    }
}

int initSerialPort(char *path, int baudrate, int flags)
{
    speed_t speed;
    int error = -1;

    /* Check arguments */
    {
        speed = getBaudrate(baudrate);
        if (speed == 1) {
            ALOGE("Invalid baudrate");
            return error;
        }
    }

    /* Opening device */
    {
        jboolean iscopy;
        if (fd > 0){
            //close(fd);
        	//����Ѿ������账��
        	return 0;
        }
        ALOGD("Opening serial port %s with flags 0x%x", path, O_RDWR | O_NOCTTY | flags);
        fd = open(path, O_RDWR | flags);
        ALOGD("open() fd = %d", fd);
        if (fd == -1)
        {
            ALOGE("Cannot open port");
            return error;
        }
    }

    /* Configure device */
    {
        struct termios cfg;
        ALOGD("Configuring serial port");
        if (tcgetattr(fd, &cfg))
        {
            ALOGE("tcgetattr() failed");
            close(fd);
            return error;
        }

        cfmakeraw(&cfg);
        cfsetispeed(&cfg, speed);
        cfsetospeed(&cfg, speed);

        if (tcsetattr(fd, TCSANOW, &cfg))
        {
            ALOGE("tcsetattr() failed");
            close(fd);
            return error;
        }
    }

    return 0;
}

void* StatusThread(void* arg)
{
    char ret[1] = {0};
    char buf[3];
    bool error_status = false;
    int led_status = 1;

    int led_fd = open("/dev/printer_led_ctrl", O_RDWR);

    while (fd > 0) {
        sleep(1);

        //Printer status check
        buf[0] = 0x1D;
        buf[1] = 0x72;
        buf[2] = 0x01;
        write(fd, buf, sizeof(buf));
        read(fd, ret, sizeof(ret));
        if (DEBUG)
            ALOGD("Printer Status Value: 0x%x", ret[0]);
        if (ret[0] & 0x0c) {
            if (DEBUG)
                ALOGD("Error status");
            error_status = true;
        } else {
            error_status = false;
        }

        if (error_status) {
            if (DEBUG)
                ALOGD("write 10101010...");
            led_status = !led_status;
            if (led_fd > 0)
                write(led_fd, &led_status , sizeof(int));
        } else {
            led_status = 1;
            if (led_fd > 0)
                write(led_fd, &led_status , sizeof(int));
        }
    }
    close(led_fd);
    return NULL;
}

void print_newline()
{
    char buf[2] = {0x0D, 0x0A};

    if (fd > 0)
        write(fd, buf, sizeof(buf));
}

COMMUNICATION_DATA_PACK constructControlDataPack(uint8_t bCommandId, uint8_t dataLength, uint8_t data[]){
	COMMUNICATION_DATA_PACK data_pack;
	data_pack.bCommandId = bCommandId;
	switch(bCommandId){
		case	CMD_GetCardSN:
		case	CMD_GetCardInfo:
				dataLength = 0;
				break;
		case	CMD_WriteCard:
				break;
	}
	//ALOGD("data[]:");
	for(int i=0; i<dataLength; i++){
		data_pack.dataBuffer[i] = data[i];
		//ALOGD("%02x",data_pack.dataBuffer[i]);
	}
	data_pack.dataBuffer[dataLength] = 0;
	data_pack.bLength = dataLength;
	return data_pack;
}

void hexToAsc(jchar* asc, uint8_t* hex, int len){
	char ascval[] = "0123456789ABCDEF";
	uint8_t* pback = hex;
	for(int i=0; i<len*2; i=i+2){
		asc[i] = ascval[(*pback)/16];
		asc[i+1] = ascval[(*pback)%16];
		pback++;
	}
}

int charTohex(char hex){
	if(hex>='0'&& hex<='9'){
		return hex-'0';
	}
	else if(hex>='A'&& hex<='F'){
		return hex-'A'+10;
	}
	else if(hex>='a'&& hex<='f'){
		return hex-'a'+10;
	}
	return 0;
}

ssize_t	tread(int fd, uint8_t *buf, size_t nbytes, unsigned int timout)
{

       int	nfds;
       fd_set	readfds;
       struct timeval  tv;
       tv.tv_sec = 0;
       tv.tv_usec = timout;
       FD_ZERO(&readfds);
       FD_SET(fd, &readfds);
       nfds = select(fd+1, &readfds, NULL, NULL, &tv);
       if (nfds <= 0) {
              if (nfds == 0)
              {
            	  //ALOGD("select waite_time out!");
              }
              //ALOGD("select error!");
              return(-1);
       }
       return(read(fd, buf, nbytes));
}

ssize_t	treadn(int fd, uint8_t *buf, size_t nbytes, unsigned int timout)

{
      size_t      nleft;
      ssize_t     nread;
      nleft = nbytes;
      while (nleft > 0) {
             if ((nread = tread(fd, buf, nleft, timout)) < 0) {
                    if (nleft == nbytes)
                    {
                    	ALOGD("treadn, error, return -1");
                    	return(-1); /* error, return -1 */
                    }
                    else{
                    	ALOGD("treadn, error, return amount read so far,break!");
                        break;      /* error, return amount read so far */
                    }
             } else if (nread == 0) {
                    break;          /* EOF */
             }
             nleft -= nread;
             buf += nread;
             ALOGD("treadn, read:%d, left:%d", nread, nleft);
      }
      return(nbytes - nleft);      /* return >= 0 */
}

#define waitCount	20		//��ֵ����ʵ��������ģ����ﱣ���������20����ʾ����20�ζ�û���յ����ݲ���Ϊ���ν��ս�����

ssize_t reliableRead(int fd, uint8_t *buf, size_t nbytes, unsigned int timout)
{
	int errorCount=0;
	int rxCnt = 0;
	int count = 0;
	uint8_t *ptr = buf;

	while(1)
	{
		rxCnt = tread(fd, ptr, nbytes, timout);
		if(-1 == rxCnt )				//�����ȡʧ��
			errorCount++;			//����ʧ�ܴ����ı��
		else
		{
			errorCount=0;			//������б�����㴦������ʧ�ܺ��ֳɹ�����µı�Ǵ���
			ptr += rxCnt;			//�ƶ�ָ��
			count += rxCnt;			//�ۼƼ���
		}
		if(errorCount >= waitCount || count>=nbytes)
		//if(errorCount >= waitCount)	//�������waitCount�ζ�û���յ����ݣ�����Ϊ��ȡ�����ݣ��������ݶ������Ͷ����½��ս���������
			break;
	}

	return count;
}

#define	WAIT_WRITE_CARD_MAX_TIME	5	//���ȴ�д������ʱ�䣬��λ�룬�˴�Ϊ3��
//������ʽ�����ݣ���������ʱ����
ssize_t NoneBlockRead(int fd, uint8_t *buf, size_t nbytes)
{
	int count =0;
	int waitNum=0;
	while(1)
	{
		count = reliableRead(fd, buf, nbytes, 1000);				//ע��˴���1��ʾ1ms��timeout
		//ALOGD("NoneBlockRead,count=%d",count);
		if(0 == count)
		{
			waitNum ++;
			if(waitNum >= (WAIT_WRITE_CARD_MAX_TIME * 1000 / waitCount))	//�ȴ�WAIT_WRITE_CARD_MAX_TIME��
				return	0;
		}
		else
		{
			return count;
		}
	}
}

void no_log_print(){

}


