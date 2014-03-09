package com.athena.broncobattle;

import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.graphics.Typeface;
import android.util.AttributeSet;
import android.view.View;

/**
 * Created by tog on 2/21/14.
 */
public class AchievementView extends View implements ValueAnimator.AnimatorUpdateListener{

    private Paint basePaint, iconPaint,gradientPaint,borderPaint,blurredPaint;
    private String text = "";
    private int color = Color.BLACK;
    private RadialGradient gradient;
    private Path glow, shine;
    private Bitmap bitmap;
    private boolean initialized = false;
    private int height = 200;
    private int width = 200;

    private float position = 0;

    public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = Character.toString((char)Integer.parseInt(text,16));
	}

	public int getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = Color.parseColor(color);
	}

	public AchievementView(Context context, AttributeSet attrs) {
        super(context, attrs);

        //TypedArray a = context.getTheme().obtainStyledAttributes(attrs, );
        /*
        try {
            text = a.getString(R.styleable.AchievementView_color);
            color = a.getColor(R.styleable.AchievementView_i,Color.DKGRAY);
        } finally {
            a.recycle();
        }
		*/
        
        basePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        borderPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

        Typeface tf = Typeface.createFromAsset(getContext().getAssets(), "fonts/entypo.ttf");
        iconPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        iconPaint.setTypeface(tf);
        iconPaint.setTextAlign(Paint.Align.CENTER);
        iconPaint.setColor(Color.WHITE);

        blurredPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        blurredPaint.setColor(Color.BLACK);
        blurredPaint.setAlpha(100);
        blurredPaint.setTypeface(tf);
        blurredPaint.setTextAlign(Paint.Align.CENTER);
        blurredPaint.setMaskFilter(new BlurMaskFilter(8, BlurMaskFilter.Blur.NORMAL));

        gradientPaint = new Paint(Paint.ANTI_ALIAS_FLAG);


        ValueAnimator mRunner = ValueAnimator.ofFloat(1, -1);
        mRunner.setDuration(1000);
        mRunner.setRepeatCount(1);
        mRunner.setRepeatMode(ValueAnimator.REVERSE);

        mRunner.addUpdateListener(this);

        mRunner.start();
        
        

    }

    public void initializeCanvas(){
        gradient = new RadialGradient(width/3,width/3,width/2,Util.lightenColor(color,0.4f),color, Shader.TileMode.MIRROR);

        glow = new Path();
        glow.reset(); // only needed when reusing this path for a new build
        glow.moveTo(0, 0); // used for first point
        glow.lineTo(width, 0);
        glow.lineTo(0, height);
        glow.lineTo(0, 0);

        shine = new Path();
        shine.reset(); // only needed when reusing this path for a new build
        shine.moveTo(0, width-width/5); // used for first point
        shine.lineTo(width-width/5, -width/5);
        shine.lineTo(width, 0);
        shine.lineTo(0, width);
        shine.lineTo(-width/5, width-width/5);

        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);

        RectF rect = new RectF();
        rect.set(0,0, width, height);

        borderPaint.setColor(Util.darkenColor(color, 0.3f));
        canvas.drawRoundRect(rect, 10, 10, borderPaint);

        borderPaint.setColor(Util.darkenColor(Util.saturateColor(color,0.3f),0.4f));
        rect.inset(height*0.05f,height*0.05f);
        canvas.drawRoundRect(rect, 10, 10, borderPaint);

        gradientPaint.setShader(gradient);
        rect.inset(height*0.02f,height*0.02f);
        canvas.drawRoundRect(rect, 10, 10, gradientPaint);

        borderPaint.setColor(Color.WHITE);
        borderPaint.setAlpha(50);
        borderPaint.setStyle(Paint.Style.FILL);

        canvas.drawPath(glow, borderPaint);

        //canvas.drawPoints(new float[]{0f,0f,(float)width,0f,0f,(float)height,0f,0f},borderPaint);
        //canvas.drawPoints(new float[]{0f,0f,20f,20f,0f,20f,0f,0f},borderPaint);

        iconPaint.setTextSize(height*0.65f);
        int xPos = (width / 2);
        int yPos = (int) ((height / 2) - ((iconPaint.descent() + iconPaint.ascent()) / 2)) ;

        blurredPaint.setTextSize(height*0.65f);
        canvas.drawText(text, xPos+1, yPos+1, blurredPaint);

        canvas.drawText(text, xPos, yPos, iconPaint);

        initialized = true;

    }

    @Override
    public void onAnimationUpdate(ValueAnimator animation) {
        position = (Float)animation.getAnimatedValue();
        invalidate();
    }

    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        if(this.isInEditMode()){
        //   return;
        }

        //canvas.rotate(position * 100);
        canvas.drawBitmap(bitmap,0,0,basePaint);
        canvas.translate(position * width, position * width);
        canvas.drawPath(shine, borderPaint);




    }

    @Override
    protected void onSizeChanged(int xNew, int yNew, int xOld, int yOld){
        super.onSizeChanged(xNew, yNew, xOld, yOld);

        width = xNew;
        height = yNew;

        if(!initialized){
            initializeCanvas();
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(widthMeasureSpec, heightMeasureSpec);
    }
}
