package pt.ulisboa.tecnico.cmov.hoponcmu.views;

import android.content.Context;
import android.graphics.Rect;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.AppCompatEditText;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class SearchEditText extends AppCompatEditText {

    int actionX, actionY;
    private Drawable drawableRight;
    private Drawable drawableLeft;
    private Drawable drawableTop;
    private Drawable drawableBottom;
    private DrawableClickListener clickListener;

    // this Constructor is required when you are using this view in xml
    public SearchEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SearchEditText(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    @Override
    public void setCompoundDrawables(Drawable left, Drawable top,
                                     Drawable right, Drawable bottom) {
        if (left != null) {
            drawableLeft = left;
        }
        if (right != null) {
            drawableRight = right;
        }
        if (top != null) {
            drawableTop = top;
        }
        if (bottom != null) {
            drawableBottom = bottom;
        }
        super.setCompoundDrawables(left, top, right, bottom);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        Rect bounds;
        if (event.getAction() == MotionEvent.ACTION_DOWN) {
            actionX = (int) event.getX();
            actionY = (int) event.getY();

            if (drawableBottom != null
                    && drawableBottom.getBounds().contains(actionX, actionY)) {
                clickListener.onClick(DrawableClickListener.DrawablePosition.BOTTOM);
                return super.onTouchEvent(event);
            }

            if (drawableTop != null
                    && drawableTop.getBounds().contains(actionX, actionY)) {
                clickListener.onClick(DrawableClickListener.DrawablePosition.TOP);
                return super.onTouchEvent(event);
            }

            if (drawableLeft != null) {
                bounds = new Rect(getPaddingRight(), getPaddingTop(),
                        getPaddingRight() + drawableLeft.getBounds().width(),
                        getPaddingTop() + drawableLeft.getBounds().height());
                if (bounds.contains(actionX, actionY)) {
                    clickListener.onClick(DrawableClickListener.DrawablePosition.LEFT);
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    return super.onTouchEvent(event);
                }
            }

            if (drawableRight != null) {
                bounds = new Rect(getWidth() - drawableRight.getBounds().width() -
                        getPaddingRight(), getPaddingTop(), getWidth() - getPaddingRight(),
                        getPaddingTop() + drawableRight.getBounds().height());
                if (bounds.contains(actionX, actionY)) {
                    clickListener.onClick(DrawableClickListener.DrawablePosition.RIGHT);
                    event.setAction(MotionEvent.ACTION_CANCEL);
                    return super.onTouchEvent(event);
                }
            }
        }
        return super.onTouchEvent(event);
    }

    @Override
    protected void finalize() throws Throwable {
        drawableRight = null;
        drawableBottom = null;
        drawableLeft = null;
        drawableTop = null;
        super.finalize();
    }

    public void setDrawableClickListener(DrawableClickListener listener) {
        this.clickListener = listener;
    }

    public interface DrawableClickListener {

        void onClick(DrawablePosition target);

        enum DrawablePosition {TOP, BOTTOM, LEFT, RIGHT}
    }
}
