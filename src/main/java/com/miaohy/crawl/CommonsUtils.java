/**
 * MIAOHY
 * 2019年6月16日下午5:24:01
 */
package com.miaohy.crawl;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class CommonsUtils {
	public static int saveUrlToTxt(StringBuilder content, String saveUrlTxtPath, String name) throws IOException {
		String[] str = content.toString().split("\r\n");
		System.out.println("段落数量： " + str.length);
		File dir = new File(saveUrlTxtPath);
		dir.mkdirs();
		File file = new File(saveUrlTxtPath + name + ".txt");
		file.createNewFile();
		BufferedWriter bfwr = new BufferedWriter(new FileWriter(file));
		for (int i = 0; i < str.length; i++) {
			bfwr.write(str[i] + "\r\n");
		}
		bfwr.close();
		System.out.println("成功将    [ " + name + " ] 保存到: " + saveUrlTxtPath);
		return str.length;
	}

	public static List<String> getUrlList(String urlTxtPath) throws IOException {
		List<String> urlList = new ArrayList<String>();
		File file = new File(urlTxtPath);
		BufferedReader bfwr = new BufferedReader(new FileReader(file));
		while (bfwr.readLine() != null) {
			String readLine = bfwr.readLine();
			String[] split = readLine.split(",");
			urlList.add(split[split.length - 1]);
		}
		bfwr.close();
		return urlList;
	}

	public static List<String> getTxtList(String urlTxtPath) throws IOException {
		List<String> txtList = new ArrayList<>();
		File file = new File(urlTxtPath);
		String[] list = file.list();
		for(String txtName:list) {
			txtList.add(txtName);
		}
		return txtList;
	}

	public static String[] getTypeList(String url) throws IOException {
		StringBuilder sb = new StringBuilder();
		File file = new File(url);
		BufferedReader bfwr = new BufferedReader(new FileReader(file));
		String string;
		while ((string = bfwr.readLine()) != null) {
			sb.append(string + "\r\n");
		}
		bfwr.close();
		String[] split = sb.toString().split("\r\n");
		return split;
	}
	public static void getResult() throws IOException {
		String typeUrl = "D:\\type.txt";
		String[] typeList = getTypeList(typeUrl);
		String txtUrl = "D:\\crawTest";
		List<String> txtList = getTxtList(txtUrl);
		Map<String, Integer> result = new HashMap<>();
		for (String type : typeList) {
			int count = 0;
			for (String txt : txtList) {
				if (txt.contains(type)) {
					count++;
				}
			}
			result.put(type, count);
		}
		int i = 0;
		for (String key : result.keySet()) {
			i++;
			if(i%2==0) {
				System.out.println(key + " ：" + result.get(key));
			}else {
				System.out.print(key + " ：" + result.get(key));
			}
				
			
			
		}
	}
    public  static void  copyTxt(String sourceFile, String targetFile){
        try{
            String encoding = "utf-8";
            File file = new File(sourceFile);
            if(file.exists() && file.isFile()){ //判断文件是否存在
                FileInputStream input = new FileInputStream(file);    //字节流
                FileOutputStream output = new FileOutputStream(new File(targetFile));
                
                InputStreamReader  read = new InputStreamReader (input,encoding);//字符流
                OutputStreamWriter write = new OutputStreamWriter(output,encoding);
                
                BufferedReader  bufferedReader  = new BufferedReader (read);//缓冲流
                BufferedWriter  bufferedWriter = new BufferedWriter(write);
                String line = "";
                while ( (line = bufferedReader.readLine() )!= null  ){
                	bufferedWriter.write(line + "\n");
                }
                bufferedReader.close();
                bufferedWriter.close();
                System.out.println(sourceFile+"   成功    ");
            }else {
                System.out.println("找不到指定的文件");
            }
        }catch(IOException e){
            System.out.println("读取文件内容出错");
            e.printStackTrace();
        }
    }
    public static void classify() throws IOException {
    	String typeUrl = "D:\\type.txt";
		String txtUrl = "D:\\crawTest";
		String typeToDir = "D:\\book2\\";
		String[] typeList = getTypeList(typeUrl);
		List<String> txtList = getTxtList(txtUrl);
		Map<String, Integer> result = new HashMap<>();
		for (String type : typeList) {
			File file = new File(typeToDir+type);
			file.mkdirs();
			int count = 0;
			for (String txt : txtList) {
				if (txt.contains(type)) {
					copyTxt(txtUrl+"\\"+txt,typeToDir+type+"\\"+txt);
					count++;
				}
			}
			result.put(type, count);
		}
    }
    public static String getNumberFormString(String source) {
    	Pattern p2 = Pattern.compile("[^0-9]");  
    	Matcher m2 = p2.matcher(source);
		return m2.replaceAll("");  
    }
	public static void main(String[] args) throws IOException {
		String string = " 2\r\n" + 
				"<br> \r\n" + 
				"";
	}
	
}
