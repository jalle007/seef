package seef.jalle.com.seef;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

public class MainActivity extends Activity {
    VideoView videoView;
    MediaController mediaController;
    private ProgressDialog progressDialog;
    private boolean firstPlay = true;
//    float leftEdge = (float) 0.3539 *width;
//    float rightEdge = (float) 0.8547*width;
//    float topEdge = 0;
//    float bottomEdge =  (float)0.603

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = (VideoView) findViewById(R.id.videoView1);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        openVideo("footagea");
        drawRect();
//
//        final ImageButton imgPlay=(ImageButton) findViewById(R.id.imgPlay);
//        imgPlay.setOnClickListener(new View.OnClickListener(){
//
//            @Override
//            public void onClick(View v){
//               imgPlay.setVisibility(View.INVISIBLE);
//                videoView.start();
//            }
//        });

        videoView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent motionEvent) {

                if (firstPlay) {
                    videoView.start();
                    firstPlay = false;
                    return false;
                } else {
                    if (isHotspot(motionEvent)) {
                        openVideo("footageb");
                        videoView.start();
                    }
                    return false;
                }
            }
        });
    }

    private void drawRect() {
        Bitmap bitmap = Bitmap.createBitmap((int) getWindowManager()
                .getDefaultDisplay().getWidth(), (int) getWindowManager()
                .getDefaultDisplay().getHeight(), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        // Rectangle

        Paint paint = new Paint();
        paint.setColor(Color.GREEN);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        paint.setStrokeWidth(10);
        float leftx = 20;
        float topy = 20;
        float rightx = 250;
        float bottomy = 200;
        canvas.drawRect(leftx, topy, rightx, bottomy, paint);
    }

    private boolean isHotspot(MotionEvent motionEvent) {
        float width = videoView.getWidth();
        float height = videoView.getHeight();
        float posx = motionEvent.getX();
        float posy = motionEvent.getY();

        float leftEdge = (float) 0.3539 * width;
        float rightEdge = (float) 0.8547 * width;
        float topEdge = 0;
        float bottomEdge = (float) 0.603 * height;

        boolean isIn = posx > leftEdge && posx < rightEdge && posy < bottomEdge && posy > topEdge;

        String msg = String.valueOf(isIn);
        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
        return isIn;
    }

    private void setProgress() {
        // create a progress bar while the video file is loading
        progressDialog = new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Seef Video View Example");
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(false);
        progressDialog.show();
    }

    private void openVideo(String file) {
        setProgress();

        if (videoView.isPlaying()) { videoView.stopPlayback(); }

        Resources res = getApplicationContext().getResources();
        int video = res.getIdentifier(file, "raw", getApplicationContext().getPackageName());

        String vid = "android.resource://" + getPackageName() + "/" + video;    // "/raw/" + "footageb.mp4";
        Uri uri = Uri.parse(vid);

        videoView.setMediaController(mediaController);
        videoView.setVideoURI(uri);
        videoView.seekTo(100);
        videoView.requestFocus();
        videoView.pause();
        progressDialog.dismiss();
        //  videoView.start();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }
}
