package dhbk.android.movienanodegree.ui.detailmovie.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;

public class ProportionalImageView extends ImageView {

    private static final float ASPECT_RATIO = 1.5f;

    public ProportionalImageView(Context context) {
        super(context);
    }

    public ProportionalImageView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public ProportionalImageView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    // FIXME: 8/11/2016 how to make imageview depends on aspect ratio
    // onMeasure có 2 tham số là chiều dài và chiều rộng con lại mà system có thể cung cấp được
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // getSize:Extracts the size from the supplied measure specification.
        // width do ta quy dịnh 144dp trong image thì nó chỉ cho chiếm 144
        int width = MeasureSpec.getSize(widthMeasureSpec);
        // còn height thì là wrapcontent nên ta có thể thay đổi height theo tỉ lệ của ta
        int height = Math.round(width * ASPECT_RATIO);
        setMeasuredDimension(width, height);
    }
}
