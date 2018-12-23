//�ⲿ��������
#ifndef __SIMCARD_JNI_H__
#define __SIMCARD_JNI_H__


#include <sys/types.h>
#include <sys/stat.h>
#include <sys/select.h>

#include <termios.h>
#include <unistd.h>
#include <fcntl.h>
#include <string.h>
#include <pthread.h>

#include <jni.h>
#include <stddef.h>
#include <assert.h>
#include <string.h>

#define SUCCESS_CODE 0

#define SUCCESS                 0
#define FAIL                    1
#define FIND_PRINTER_FAIL       2
#define READ_ERROR				3	//���ڶ�ȡ����
#define DATA_LEN_ERROR			4	//���ݳ��ȴ���
#define COMMAND_BLOCK			5	//����״̬������ִ��
#define DATA_LEN_ZERO			6	//������
#include <android/log.h>

#define LOG_TAG "SimCard_JNI"
#define DEBUG 1
#if DEBUG
#define ALOGD(...) __android_log_print(ANDROID_LOG_DEBUG, LOG_TAG, __VA_ARGS__)
#else
#define ALOGD(...)	no_log_print()
#endif
#define ALOGE(...) __android_log_print(ANDROID_LOG_ERROR, LOG_TAG, __VA_ARGS__)

//������ӳ���
#define CMD_GetCardSN         		0 		//	int GetCardSN ([out] char * CardSN)
#define CMD_GetCardInfo        		1  		//	int GetCardInfo ([out] char * CardInfo)
#define CMD_WriteCard        		2		//	int WriteCard ( [in] char * IssueData��[out] char* Result)

//�ȴ�������Ӧ��ʱʱ��
#define COM_WAITE_TIMEOUT			10

#define JNIREG_CLASS "com/newland/nativepackage/SimCard"//ָ��Ҫע�����
//#define JNIREG_CLASS "com/boshidu/simcardwritertest/SimCard"
//#define JNIREG_CLASS "com/cmcc/nativepackage/SimCard"

#define MIN(A,B) ((A)<(B))?(A):(B)
#define MAX(A,B) ((A)>(B))?(A):(B)

speed_t getBaudrate(int baudrate);

int initSerialPort(char *path, int baudrate, int flags);

void* StatusThread(void* arg);

void print_newline();

void hexToAsc(jchar* asc, uint8_t* hex, int len);

int charTohex(char hex);

//ͨ�����ݰ������ڻ�������λ����ͨ������
#define MAX_COM_DATA_PACK_SIZE 	512
//�������ݰ����������
typedef struct
{
	uint8_t  bCommandId;      //������
	uint8_t  bLength;         //dataBuffer����Ч���ݳ���
	uint8_t  dataBuffer[MAX_COM_DATA_PACK_SIZE];
}COMMUNICATION_DATA_PACK;

//�������ݰ������÷��ؽ����
typedef struct
{
	uint8_t  bResult;      //������
	uint8_t  bLength;         //dataBuffer����Ч���ݳ���
	uint8_t  dataBuffer[MAX_COM_DATA_PACK_SIZE];
}COMMUNICATION_RESP_PACK;

//��������������ݰ�
COMMUNICATION_DATA_PACK constructControlDataPack(uint8_t bCommandId, uint8_t dataLength, uint8_t data[]);

ssize_t	tread(int fd, uint8_t *buf, size_t nbytes, unsigned int timout);

ssize_t	treadn(int fd, uint8_t *buf, size_t nbytes, unsigned int timout);

ssize_t reliableRead(int fd, uint8_t *buf, size_t nbytes, unsigned int timout);

ssize_t NoneBlockRead(int fd, uint8_t *buf, size_t nbytes);

JNIEXPORT jint JNICALL Java_com_bsdtec_nativepackage_SimCardJni_WriteCard(
		JNIEnv * env, jobject obj, jcharArray IssueData, jcharArray Result);

void com_read_write_test();

void no_log_print();

bool set_block_command();

int release_block_command(int return_code);

#endif

