package com.yiwo.fuzhoudian.tongban_emoticon;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.text.Html;
import android.util.Log;
import android.widget.TextView;

public  class String2HtmlTextTools {
    public static void tvSetHtmlForImage(final Context context, TextView textView, String string){
        Log.d("拆分前：：",string);
        String [] strings = string.split("]");
        String[] stringss;
        for (int i = 0 ; i<strings.length ;i++){
            if (i<strings.length-1 || (i == strings.length-1 && string.charAt(string.length()-1) == ']')){
                strings[i]+="]";
            }
            Log.d("拆分后转码前：：",strings[i]);
            stringss = strings[i].split("\\[");
            for (int k = 0;k<stringss.length;k++){
                if(k>0) stringss[k] = "[" + stringss[k];
                Log.d("拆分：：",stringss[k]);
                for (int j = 0; j < EmotionNames.NAMES.length;j++){
                    if (EmotionNames.NAMES[j].equals(stringss[k])){
                        Log.d("拆分：遇到表情：",stringss[k]+"///"+EmotionNames.NAMES[j]);
                        stringss[k] = "<img src=\"" + context.getResources().getIdentifier("em_"+(j+1), "mipmap",context.getPackageName()) + "\"/>";
                        Log.d("拆分：变成Html表情：",stringss[k]+"///");
                        break;
                    }
                }
            }
            strings[i] ="";
            for (String s :stringss){
                strings[i] += s;
            }
            Log.d("拆分后转码后：：",strings[i]);
        }
        Html.ImageGetter imgGetterFromProject = new Html.ImageGetter() {
            @Override
            public Drawable getDrawable(String source) {
                Drawable drawable = null;
                int rId = Integer.parseInt(source);
                drawable = context.getResources().getDrawable(rId);
                drawable.setBounds(0, 0, drawable.getIntrinsicWidth(),
                        drawable.getIntrinsicHeight());
                return drawable;
            }
        };
        String strFinal = "";
        for (String s :strings){
            strFinal += s;
        }
        Log.d("拆分再拼接后：：",strFinal);
        textView.setText(Html.fromHtml(strFinal,imgGetterFromProject,null));
    }
}
