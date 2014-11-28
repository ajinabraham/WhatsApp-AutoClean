package opensecurity.whatsappautoclean;
import android.app.Service;
import android.content.Intent;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;
import java.io.File;

public class AutoCleanService extends Service {

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        //TODO do something useful

        try {

                   File path = new File(Environment.getExternalStorageDirectory() + "/WhatsApp/Media/");
                   File wpath = new File(Environment.getExternalStorageDirectory() + "/WhatsApp/");
                   deleteDir(path);
                   createNomedia(wpath);
                   Log.d("WhatsAppAutoClean", "Deleted WhatsApp Media Directory");

        } catch (Exception e) {
            Log.e("WhatsAppAutoClean", "exception", e);
        }
        return Service.START_NOT_STICKY;
    }

    @Override
    public IBinder onBind(Intent intent) {
        //TODO for communication return IBinder implementation
        return null;
    }

    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }


    public static boolean deleteDir(File dir) {
        if (dir.isDirectory()) {
            String[] children = dir.list();
            for (int i=0; i<children.length; i++) {
                boolean success = deleteDir(new File(dir, children[i]));
                if (!success) {
                    return false;
                }
            }
        }

        return dir.delete();
    }
    public static void createNomedia(File dir)
    {

        File med = new File (dir.getAbsolutePath() + "/Media");
        med.mkdirs();
        File file = new File(med, ".nomedia");
        if (!file.exists()) {
            try {
                file.createNewFile();
            }
            catch (Exception e) {
                Log.e("WhatsAppAutoClean", "exception", e);
            }
        }

    }
}