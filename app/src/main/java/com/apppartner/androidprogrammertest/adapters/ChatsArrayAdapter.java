package com.apppartner.androidprogrammertest.adapters;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.apppartner.androidprogrammertest.R;
import com.apppartner.androidprogrammertest.customViews.RoundedImageView;
import com.apppartner.androidprogrammertest.models.ChatData;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created on 12/23/14.
 *
 * @author Thomas Colligan
 */
public class ChatsArrayAdapter extends ArrayAdapter<ChatData>
{
    private final Map<Integer, Bitmap> userImages = new HashMap<>();
    private Bitmap bitmap;
    private Context context;

    public ChatsArrayAdapter(Context context, List<ChatData> objects)
    {
        super(context, 0, objects);
        this.context = context;
    }

    public View getView(int position, View convertView, ViewGroup parent)
    {
        ChatCell chatCell = new ChatCell();

        LayoutInflater inflater = LayoutInflater.from(getContext());
        convertView = inflater.inflate(R.layout.cell_chat, parent, false);

        chatCell.circleView = (RoundedImageView) convertView.findViewById(R.id.circleImageView);
        chatCell.usernameTextView = (TextView) convertView.findViewById(R.id.usernameTextView);
        chatCell.messageTextView = (TextView) convertView.findViewById(R.id.messageTextView);

        ChatData chatData = getItem(position);

        chatCell.usernameTextView.setText(chatData.username);
        Typeface typeFace= Typeface.createFromAsset(context.getAssets(), "fonts/Jelloween - Machinato.ttf");
        chatCell.usernameTextView.setTypeface(typeFace);
        typeFace= Typeface.createFromAsset(context.getAssets(), "fonts/Jelloween - Machinato Light.ttf");
        chatCell.messageTextView.setText(chatData.message);
        chatCell.messageTextView.setTypeface(typeFace);

        bitmap = userImages.get(chatData.userID);

        if(bitmap != null){
            chatCell.circleView.setImageBitmap(bitmap);
        }else {
            new ImageDownloader(chatCell.circleView, chatData).execute(chatData.avatarURL);
        }

        return convertView;
    }

    private static class ChatCell
    {
        TextView usernameTextView;
        TextView messageTextView;
        RoundedImageView circleView;
    }

    private class ImageDownloader extends AsyncTask<String, Void, Bitmap> {
        private RoundedImageView bmImage;
        private ChatData chatData;

        public ImageDownloader(RoundedImageView bmImage, ChatData chatData) {
            this.bmImage = bmImage;
            this.chatData = chatData;
        }

        protected Bitmap doInBackground(String... urls) {
            String url = urls[0];
            Bitmap mIcon = null;
            try {
                InputStream in = new java.net.URL(url).openStream();
                mIcon = BitmapFactory.decodeStream(in);
            } catch (Exception e) {
                Log.e("Error", e.getMessage());
            }
            return mIcon;
        }

        protected void onPostExecute(Bitmap result) {
            bmImage.setImageBitmap(result);
            userImages.put(chatData.userID, result);
        }
    }
}
