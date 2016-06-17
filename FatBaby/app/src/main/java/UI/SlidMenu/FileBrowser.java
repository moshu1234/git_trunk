package com.example.andrewliu.fatbaby.UI.SlidMenu;

import android.app.Fragment;
import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.andrewliu.fatbaby.DataBase.FileService;
import com.example.andrewliu.fatbaby.R;

import java.security.spec.ECField;

/**
 * Created by liut1 on 6/16/16.
 */
public class FileBrowser extends Fragment{
    private View view;
    private String fileContent="a";
    private String fileName = "/test.html";
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        view = inflater.inflate(R.layout.file_browser, container, false);
        return view;
    }

    public void setButtonListen(){
        Button buttonA = (Button)view.findViewById(R.id.file_download);
        buttonA.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                saveFile(fileName,fileContent);
            }
        });
        Button buttonB = (Button)view.findViewById(R.id.file_open);
        buttonB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                readFile(fileName);
            }
        });
    }
    public void readFile(String filename){
        TextView textView = (TextView)view.findViewById(R.id.file_text);
        FileService fileService = new FileService(getActivity());
        try {
            textView.setText(fileService.read(filename));
        }catch (Exception e){
            Log.e("readFile",e.getMessage());
        }
    }

    public void saveFile(String fileName, String fileContent){
        FileService fileService = new FileService(getActivity());
        try {
            fileService.save(fileName,fileContent);
        }catch (Exception e){
            Log.e("saveFile",e.getMessage());
        }
    }
}
