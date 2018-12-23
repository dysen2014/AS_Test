package com.guoguang.jni;

public class JniCallTest {
    static {
        System.loadLibrary("JniCallTest");
        System.loadLibrary("idcaread");
    }

    public native static String idcardmsg(String hexStr);
    /**
     * 计算工作密钥pinkey
     * @param source1 48字节hex码
     *  (jni调用所能byte[] 结束要加'\0')
     * @return
     */
    //public native byte[] pinkey(byte [] workkey,byte [] primarykey);

    /**
     * 计算工作密钥 mackey
     *
     * @param source1 48字节hex码
     *                (jni调用所能byte[] 结束要加'\0')
     * @return
     */
    public native byte[] mackey(byte[] workkey, byte[] primarykey);

    /**
     * 计算主密钥效验码
     *
     * @param source1 32字节hex码
     *                (jni调用所能byte[] 结束要加'\0')
     * @return
     */
    public native String checkkey(String source);

    /**
     * 计算主密钥
     *
     * @param source1 32字节hex码 第一段
     * @param source2 32字节hex码 第二段
     * @param source3 32字节hex码 第三段
     *                (jni调用所能byte[] 结束要加'\0')
     * @return
     */
    public native byte[] primarykey(String source1, String source2, String source3);

    /**
     * 计算报文MAC值
     *
     * @param source
     * @param in_key (jni调用所能byte[] 结束要加'\0')
     * @return
     */
    public native byte[] getmac(byte[] source, byte[] in_key, int iLength);

    /**
     * 计算用户密码
     * @param pin_key 16字节密钥
     * @param v_card_no 用户卡号
     * @param v_pin 密码明文，不超过8字节
     * (jni调用所能byte[] 结束要加'\0')
     */
    //public native byte[] enpin(byte [] pin_key, byte[] v_card_no,byte[] v_pin);

    /**
     * bluetooth encrypt
     *
     * @param source
     * @param sourcelen
     * @param deskey
     * @param flg
     * @return
     */
    public native byte[] encrypt(byte[] source, int sourcelen, byte[] deskey, int flg);

    /**
     * fingerprint create bmp
     *
     * @param source
     * @param sourcelen
     * @param deskey
     * @param flg
     * @return
     */
    public native static byte[] writeBMP(byte[] file, byte[] image, int X, int Y);

    public native int wlt2bmp(byte[] src, int inlen, byte[] outbuf, int outlen, int bmpSave);

}
