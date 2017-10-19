package com.pactera.financialmanager.util;

public class IDCReaderSDK
{
  static
  {
    System.loadLibrary("wltdecode");
  }

  public static int decodingPictureData(String wltlibDirectory, byte[] pictureData)
  {
    int ret = wltInit(wltlibDirectory);
    if (ret != 0) {
      return 1;
    }
    byte[] datawlt = new byte[1384];
    byte[] byLicData = { 5, 0, 1, 0, 91, 3, 51, 1, 90, -77, 30 };

    for (int i = 0; i < 1024; i++) {
      datawlt[(i + 14 + 256)] = pictureData[i];
    }
    ret = wltGetBMP(datawlt, byLicData);
    if (ret == 2) {
      return 2;
    }
    if (ret != 1) {
      return 3;
    }
    return 0;
  }

  public static native int wltInit(String paramString);

  public static native int wltGetBMP(byte[] paramArrayOfByte1, byte[] paramArrayOfByte2);
}