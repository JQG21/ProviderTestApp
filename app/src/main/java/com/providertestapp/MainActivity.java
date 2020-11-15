package com.providertestapp;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.database.Cursor;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    final static String TAG = "myTag:";

    Button all,insert,del,del_all,alt,search;
    private ContentResolver resolver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        resolver = this.getContentResolver();

        all = findViewById(R.id.all);
        insert = findViewById(R.id.insert);
        del = findViewById(R.id.del);
        del_all = findViewById(R.id.del_all);
        alt = findViewById(R.id.alt);
        search = findViewById(R.id.search);

        //得到全部
        all.setOnClickListener(view -> {
            Cursor cursor = resolver.query(Words.Word.CONTENT_URI,
                    new String[] { Words.Word._ID, Words.Word.COLUMN_NAME_WORD,Words.Word.COLUMN_NAME_MEANING,Words.Word.COLUMN_NAME_SAMPLE},
                    null, null, null);
            if (cursor == null){
                Toast.makeText(MainActivity.this,"没有找到记录",Toast.LENGTH_LONG).show();
                return;
            }

            //找到记录，这里简单起见，使用Log输出

            StringBuilder msg = new StringBuilder();
            if (cursor.moveToFirst()){
                do{
                    msg.append("ID:").append(cursor.getInt(cursor.getColumnIndex(Words.Word._ID))).append(",");
                    msg.append("单词：").append(cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_WORD))).append(",");
                    msg.append("含义：").append(cursor.getInt(cursor.getColumnIndex(Words.Word.COLUMN_NAME_MEANING))).append(",");
                    msg.append("示例").append(cursor.getFloat(cursor.getColumnIndex(Words.Word.COLUMN_NAME_SAMPLE))).append("\n");
                }while(cursor.moveToNext());
            }

            Log.v(TAG, msg.toString());
            cursor.close();

        });

        //增加
        insert.setOnClickListener(view -> {
            String word="Banana";
            String meaning="banana";
            String sample="This banana is very nice.";
            ContentValues values = new ContentValues();

            values.put(Words.Word._ID,GUID.getGUID());
            values.put(Words.Word.COLUMN_NAME_WORD, word);
            values.put(Words.Word.COLUMN_NAME_MEANING, meaning);
            values.put(Words.Word.COLUMN_NAME_SAMPLE, sample);

            Uri newUri = resolver.insert(Words.Word.CONTENT_URI, values);
        });

        //删除
        del.setOnClickListener(view -> {
            String id="3";//简单起见，这里指定ID，用户可在程序中设置id的实际值
            Uri uri = Uri.parse(Words.Word.CONTENT_URI_STRING + "/" + id);
            int result = resolver.delete(uri, null, null);
        });

        //删除全部
        del_all.setOnClickListener(view -> resolver.delete(Words.Word.CONTENT_URI, null, null));

        //修改
        alt.setOnClickListener(view -> {
            String id="3";
            String strWord="Banana";
            String strMeaning="banana";
            String strSample="This banana is very nice.";
            ContentValues values = new ContentValues();

            values.put(Words.Word.COLUMN_NAME_WORD, strWord);
            values.put(Words.Word.COLUMN_NAME_MEANING, strMeaning);
            values.put(Words.Word.COLUMN_NAME_SAMPLE, strSample);

            Uri uri = Uri.parse(Words.Word.CONTENT_URI_STRING + "/" + id);
            int result = resolver.update(uri, values, null, null);

        });

        //根据id查询
        search.setOnClickListener(view -> {
            String id="3";
            Uri uri = Uri.parse(Words.Word.CONTENT_URI_STRING + "/" + id);
            Cursor cursor = resolver.query(Words.Word.CONTENT_URI,
                    new String[] { Words.Word._ID, Words.Word.COLUMN_NAME_WORD, Words.Word.COLUMN_NAME_MEANING,Words.Word.COLUMN_NAME_SAMPLE},
                    null, null, null);
            if (cursor == null){
                Toast.makeText(MainActivity.this,"没有找到记录",Toast.LENGTH_LONG).show();
                return;
            }

            //找到记录，这里简单起见，使用Log输出

            StringBuilder msg = new StringBuilder();
            if (cursor.moveToFirst()){
                do{
                    msg.append("ID:").append(cursor.getInt(cursor.getColumnIndex(Words.Word._ID))).append(",");
                    msg.append("单词：").append(cursor.getString(cursor.getColumnIndex(Words.Word.COLUMN_NAME_WORD))).append(",");
                    msg.append("含义：").append(cursor.getInt(cursor.getColumnIndex(Words.Word.COLUMN_NAME_MEANING))).append(",");
                    msg.append("示例").append(cursor.getFloat(cursor.getColumnIndex(Words.Word.COLUMN_NAME_SAMPLE))).append("\n");
                }while(cursor.moveToNext());
            }
            Log.v(TAG, msg.toString());
        });
    }
}