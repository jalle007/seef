package seef.jalle.com.seef;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.res.Resources;
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
    View hotspot ;
    float width,height;
    float leftEdge, rightEdge , topEdge , bottomEdge;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        videoView = (VideoView) findViewById(R.id.videoView1);
        mediaController = new MediaController(this);
        mediaController.setAnchorView(videoView);

        openVideo("footagea");

//        hotspot=(View)findViewById(R.id.hotspot);
//        drawRect();
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
                        String msg = "playing footage b";
                        Toast.makeText(getApplicationContext(), msg, Toast.LENGTH_SHORT).show();
                    }
                    return false;
                }
            }
        });
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        // TODO Auto-generated method stub
        super.onWindowFocusChanged(hasFocus);

//        width =videoView.getWidth();
//        height  =videoView.getWidth();
//        leftEdge = (float) 0.3539 * width;
//        rightEdge = (float) 0.8547 * width;
//        topEdge = 0;
//        bottomEdge = (float) 0.603 * height;
//
//        if (firstPlay)
//        {
//            RelativeLayout.LayoutParams layoutParams = (RelativeLayout.LayoutParams) hotspot.getLayoutParams();
//            layoutParams.leftMargin = (int) leftEdge;
//            layoutParams.topMargin = (int) topEdge;
//            layoutParams.rightMargin = (int) (rightEdge -width);
//            layoutParams.bottomMargin =(int) (bottomEdge-height);
//            hotspot.setLayoutParams(layoutParams);
//            hotspot.setVisibility(View.VISIBLE);
//        }
    }
    private void drawRect() {
        hotspot.setVisibility(View.VISIBLE);
    }

    private boolean isHotspot(MotionEvent motionEvent) {
        float posx = motionEvent.getX();
        float posy = motionEvent.getY();

        width = videoView.getWidth();
        height = videoView.getHeight();
        leftEdge = (float) 0.3539 * width;
        rightEdge = (float) 0.8547 * width;
        topEdge = 0;
        bottomEdge = (float) 0.603 * height;

        boolean isIn = posx > leftEdge && posx < rightEdge && posy < bottomEdge && posy > topEdge;
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

    public void openVideo(String file) {
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
