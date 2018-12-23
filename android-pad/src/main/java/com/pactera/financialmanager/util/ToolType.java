package com.pactera.financialmanager.util;

/**
 * Created by Tiger on 2017/10/13.
 */

public class ToolType {

    //区间菜单部分
    public static final String[] intervalList = new String[]{"大于", "小于", "区间"};

//         040   "农户客户",
//           010 "对公客户",
//         030   "个人客户"

    //客户类型菜单
    public static final String[] customerList =
            new String[]{"农户客户", "对公客户", "个人客户"};
    //证件类型菜单
    //    0	身份证
    //010	组织机构代码证
    //    020	营业执照
    //030	统一社会信用代码证
    //090	其他企业证件
    //095	境外营业执照
    //1	户口簿
    //2	护照
    //3	军官证
    //4	士兵证
    //5	港澳居民来住内地通行证
    //6	台胞同胞来往内地通行证
    //7	临时身份证
    //8	外国人居留证
    //9	警官证
    //    X	其他证件


    public static final String[] certificateList =
            new String[]{"身份证","组织机构代码证","营业执照", "统一社会信用代码证",
                    "其他企业证件", "境外营业执照", "户口簿", "护照",
                    "军官证", "士兵证", "港澳居民来住内地通行证", "台胞同胞来往内地通行证",
                    "临时身份证", "外国人居留证", "警官证", "其他证件"};

    //    AAA	AAA
    //    AA+	AA+
    //    AA	AA
    //    AA-	AA-
    //    A+	A+
    //    A	A
    //    A-	A-
    //    BBB+	BBB+
    //    BBB	BBB
    //    BBB-	BBB-
    //    BB	BB
    //    B	B
    //    C	C
    //    D	D
    //评价等级
    public static final String[] ratingList =
            new String[]{"AAA", "AA+", "AA", "AA-", "A+",
                    "A", "A-", "BBB+", "BBB", "BBB-",
                    "BB", "B", "C", "D"};

//    	01	正常
//    	0101	正常1
//    	0102	正常2
//    	0103	正常3
//    	02	关注
//    	0201	关注1
//    	0202	关注2
//    	0203	关注3
//    	03	次级
//    	0301	次级1
//    	0302	次级2
//    	04	可疑
//    	0401	可疑1
//    	05	损失
//    	0501	损失1
    //五级分类
    public static final String[]  fiveCategoriesList =
            new String[]{"正常" ,"正常1" ,"正常2" ,"正常3" ,"关注" ,"关注1"
                    ,"关注2" ,"关注3" ,"次级" ,"次级1" ,"次级2" ,"可疑" ,"可疑1"
                    ,"损失" ,"损失1"};


    public String tollTypeReturn(String typeName){

        String returnType = null;
        switch (typeName){
            case "大于":
                returnType = "a";
                break;
            case "小于":
                returnType = "b";
                break;
            case "区间":
                returnType = "c";
                break;
            case "农户客户":
                returnType = "040";
                break;
            case "对公客户":
                returnType = "010";
                break;
            case "个人客户":
                returnType = "030";
                break;
            case "身份证":
                returnType = "0";
                break;
            case "组织机构代码证":
                returnType = "010";
                break;
            case "营业执照":
                returnType = "020";
                break;
            case "030":
                returnType = "统一社会信用代码证";
                break;
            case "其他企业证件":
                returnType = "090";
                break;
            case "境外营业执照":
                returnType = "095";
                break;
            case "户口簿":
                returnType = "1";
                break;
            case "护照":
                returnType = "2";
                break;
            case "军官证":
                returnType = "3";
                break;
            case "士兵证":
                returnType = "4";
                break;
            case "港澳居民来住内地通行证":
                returnType = "5";
                break;
            case "台胞同胞来往内地通行证":
                returnType = "6";
                break;
            case "临时身份证":
                returnType = "7";
                break;
            case "外国人居留证":
                returnType = "8";
                break;
            case "警官证":
                returnType = "9";
                break;
            case "其他证件":
                returnType = "X";
                break;
            case "AAA":
                returnType = "AAA";
                break;
            case "AA+":
                returnType = "AA+";
                break;
            case "AA":
                returnType = "AA";
                break;
            case "AA-":
                returnType = "AA-";
                break;
            case "A+":
                returnType = "A+";
                break;
            case "A":
                returnType = "A";
                break;
            case "A-":
                returnType = "A-";
                break;
            case "BBB+":
                returnType = "BBB+";
                break;
            case "BBB":
                returnType = "BBB";
                break;
            case "BBB-":
                returnType = "BBB-";
                break;

            case "BB":
                returnType = "BB";
                break;

            case "B":
                returnType = "B";
                break;

            case "C":
                returnType = "C";
                break;

            case "D":
                returnType = "D";
                break;

            case "正常":
                returnType = "01";
                break;

            case "正常1":
                returnType = "0101";
                break;

            case "正常2":
                returnType = "0102";
                break;
            case "正常3":
                returnType = "0103";
                break;
            case "关注":
                returnType = "02";
                break;
            case "关注1":
                returnType = "0201";
                break;
            case "关注2":
                returnType = "0202";
                break;
            case "关注3":
                returnType = "0203";
                break;
            case "次级":
                returnType = "03";
                break;
            case "次级1":
                returnType = "0301";
                break;
            case "次级2":
                returnType = "0302";
                break;
            case "可疑":
                returnType = "04";
                break;
            case "可疑1":
                returnType = "0401";
                break;
            case "损失":
                returnType = "05";
                break;
            case "损失1":
                returnType = "0501";
                break;
        }

        return returnType;
    }

}
