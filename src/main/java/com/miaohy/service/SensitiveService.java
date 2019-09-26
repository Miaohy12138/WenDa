/**
 * Author : MIAOHY
 * Time :2019/8/12 13:13
 * Beauty is better than ugly!
 */
package com.miaohy.service;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

@Service
public class SensitiveService implements InitializingBean {

    private Node root = new Node();

    private static final String DEFAULT_REPLACEMENT = "***";

    private void addWord(String lineTxt){
        Node temp = root;

        for (int i = 0; i < lineTxt.length(); i++) {
            Character character = lineTxt.charAt(i);
            // 过滤空格
            if (isSymbol(character)) {
                continue;
            }
            Node node = temp.getSubNode(character);
            if(node==null){
                node = new Node();
                temp.addSubNode(character,node);
            }

            temp = node;

            if(i==lineTxt.length()-1){
                temp.setKeyWordEnd(true);
            }
        }
    }
    public String filter(String text){
        if (StringUtils.isBlank(text)) {
            return text;
        }
        String replacement = DEFAULT_REPLACEMENT;
        StringBuilder sb = new StringBuilder();
        Node tempNode = root;
        int begin = 0;
        int position = 0;

        while(position<text.length()){
            char c = text.charAt(position);
            if(isSymbol(c)){
                if(tempNode ==root){
                    sb.append(c);
                    begin++;
                }
                position++;
                continue;
            }
            tempNode = tempNode.getSubNode(c);
            if(tempNode==null){
                sb.append(text.charAt(begin));
                position = begin+1;
                begin = position;
                tempNode = root;

            }else if(tempNode.isKeyWordEnd()){
                sb.append(replacement);
                position = position +1;
                begin = position;
                tempNode = root;
            }else {
                position++;
            }
        }
        sb.append(text.substring(begin));

        return sb.toString();
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        try{
            InputStream is = Thread.currentThread().getContextClassLoader().getResourceAsStream("SensitiveWords.txt");
            InputStreamReader inputStreamReader = new InputStreamReader(is);
            BufferedReader bfr = new BufferedReader(inputStreamReader);
            String line ;
            while((line =bfr.readLine())!=null){
                line = line.trim();
                addWord(line);
            }
            is.close();
        }catch (Exception e){
            System.err.print("敏感词文件读取出错");
        }

    }

    /**
     * 判断是否是一个符号
     */
    private boolean isSymbol(char c) {
        int ic = (int) c;
        // 0x2E80-0x9FFF 东亚文字范围
        return !CharUtils.isAsciiAlphanumeric(c) && (ic < 0x2E80 || ic > 0x9FFF);
    }

    private class Node {
        private boolean end = false;

        private Map<Character, Node> subNodes = new HashMap<>();

        void addSubNode(Character key, Node node) {

            subNodes.put(key, node);
        }

        Node getSubNode(Character key) {
            return subNodes.get(key);
        }

        boolean isKeyWordEnd() {
            return end;
        }

        void setKeyWordEnd(boolean end) {
            this.end = end;
        }

        public int getSubNodeCount() {
            return subNodes.size();
        }

    }

    public static void main(String[] args) {
        SensitiveService s = new SensitiveService();
        s.addWord("色情");
        s.addWord("呵呵");
        
        System.out.print(s.filter("你好色情是不是呵 呵"));
    }




}
