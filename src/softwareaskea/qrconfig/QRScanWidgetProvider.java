package softwareaskea.qrconfig;

import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.Context;
import android.widget.Toast;

public class QRScanWidgetProvider extends AppWidgetProvider {
	@Override
    public void onUpdate(Context context,
                 AppWidgetManager appWidgetManager,
                 int[] appWidgetIds) {
		Toast.makeText(context, "Widget updated", Toast.LENGTH_SHORT);
	}
}
