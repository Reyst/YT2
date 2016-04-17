package gsihome.reyst.y2t.data;

import android.annotation.SuppressLint;
import android.content.Context;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Random;

import gsihome.reyst.y2t.R;

public class DataUtil {

    public static final int MAGIC_N1 = 10_000_000;

    private static DateFormat sFormatter;

    public static List<IssueEntity> getModel(Context ctx, State state) {

        String[] urls = ctx.getResources().getStringArray(R.array.image_urls);

        List<IssueEntity> result = new ArrayList<>(10);

        Random r = new Random((new Date()).getTime());

        for (int i = 1; i <= 10; i++) {

            @SuppressLint("DefaultLocale")
            int randomInt = r.nextInt(MAGIC_N1);
            String number = String.format("CE-%d08", randomInt);

            String category = "";
            String responsible = "";
            int iconId = 0;

            switch (randomInt % 2) {
                case 0:
                    category = ctx.getString(R.string.category1);
                    responsible = ctx.getString(R.string.responsibile1);
                    iconId = R.drawable.icon1;
                    break;
                case 1:
                    category = ctx.getString(R.string.category2);
                    responsible = ctx.getString(R.string.responsibile2);
                    iconId = R.drawable.icon2;
                    break;
            }

            Date dtCreated = new Date();
            dtCreated.setDate(r.nextInt(31) + 1);
            dtCreated.setMonth(2);

            result.add(new IssueEntity(i, number, category, state, dtCreated, new Date(), new Date(),
                    responsible, iconId, randomInt % 3,
                    "Полное описание задачи № " + number + ".This is full description of the task.",
                    Arrays.asList(urls)));
        }

        return result;
    }

    public static DateFormat getFormatter() {

        if (sFormatter == null) {
            sFormatter = new SimpleDateFormat("MMM d, yyyy", Locale.getDefault());
        }

        return sFormatter;
    }
}
