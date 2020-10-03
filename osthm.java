// This java code must be injected into an app to work, or you might use os-thm-sketchware-asd.java if you can't inject it.
package tw.osthm;

import android.graphics.Color;
import android.graphics.Bitmap;
import android.os.Environment;
import android.util.Log;

import java.io.*;
import java.nio.channels.FileChannel;
import java.util.*;

import org.json.JSONException;
import org.json.JSONObject;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

public class osthm {
    
    public static final String externalDir = Environment.getExternalStorageDirectory().getAbsolutePath();
    public static final String themes_folder = externalDir + "/.os-thm/themes/";
    public static final String config_file = externalDir + "/.os-thm/conf";
    public static final String exported_themes_folder = externalDir + "/os-thm/";
    
    // Load config file into HashMap
    private static HashMap<String, String> loadConfJson() {
        try {
            return new Gson().fromJson(StorageUtil.readFile(config_file),
                    new TypeToken<HashMap<String, String>>() {
                    }.getType());
        } catch (IOException e) {
            return null;
        }
    }
    
    private static void setConf(String key, String value) {
        HashMap<String, String> data = loadConfJson();
        data.put(key, value);
        StorageUtil.createFile(config_file, new Gson().toJson(data));
    }

    private static String getConf(String key, String defaultValue) {
        HashMap<String, String> data = loadConfJson();
        return data.containsKey(key) ? data.get(key) : defaultValue;
    }
    
    private static boolean containsConf(String key) {
        HashMap<String, String> data = loadConfJson();
        return data.containsKey(key);
    }
    
    public static ArrayList<HashMap<String, Object>> getDefaultThemes() {
        ArrayList<HashMap<String, Object>> defaultThemes = new ArrayList<>();
        defaultThemes = new ArrayList<>();
        defaultThemes.add(0, addKeyToHashMap("themesname", "Vanilla"));
        defaultThemes.get(0).put("themesjson", "{\"colorPrimary\":-14575885,\"colorBackgroundCardTint\":-16777216,\"colorPrimaryDark\":-15242838,\"colorBackgroundText\":-16777216,\"colorBackground\":-1,\"shadow\":1,\"colorPrimaryTint\":-1,\"colorHint\":-5723992,\"colorStatusbarTint\":1,\"colorPrimaryCardTint\":-16777216,\"colorAccent\":-720809,\"colorPrimaryText\":-1,\"colorBackgroundCardText\":-16777216,\"colorBackgroundTint\":-16777216,\"colorControlHighlight\":1073741824,\"colorAccentText\":-1,\"colorBackgroundCard\":-1,\"colorPrimaryCardText\":-16777216,\"colorPrimaryCard\":-1,\"colorDialog\":-1,\"colorDialogText\":-16777216,\"colorDialogTint\":-16777216}");
        defaultThemes.get(0).put("themesinfo", "The default style theme of os-thm");
        defaultThemes.get(0).put("themesauthor", "リェンーゆく");
        defaultThemes.get(0).put("os-thm-version", metadataVersion);
        defaultThemes.get(0).put("uuid", "default");
        defaultThemes.get(0).put("theme-version", 3);
        defaultThemes.add(1, addKeyToHashMap("themesname", "Dark"));
        defaultThemes.get(1).put("themesjson", "{\"colorPrimary\":-13421773,\"colorBackgroundCardTint\":-7762803,\"colorPrimaryDark\":-14540254,\"colorBackgroundText\":-1,\"colorBackground\":-14342875,\"shadow\":1,\"colorDialog\":-13685459,\"colorPrimaryTint\":-1,\"colorHint\":-8355712,\"colorStatusbarTint\":1,\"colorDialogTint\":-7762803,\"colorPrimaryCardTint\":-7762803,\"colorPrimaryText\":-1,\"colorAccent\":-720809,\"colorBackgroundCardText\":-2038811,\"colorBackgroundTint\":-1,\"colorControlHighlight\":1090519039,\"colorAccentText\":-1,\"colorDialogText\":-2038811,\"colorBackgroundCard\":-13685459,\"colorPrimaryCardText\":-2038811,\"colorPrimaryCard\":-13685459}");
        defaultThemes.get(1).put("themesinfo", "A Material dark theme for os-thm");
        defaultThemes.get(1).put("themesauthor", "thatcakepiece");
        defaultThemes.get(1).put("os-thm-version", metadataVersion);
        defaultThemes.get(1).put("uuid", "dark");
        defaultThemes.get(1).put("theme-version", 4);
        return defaultThemes;
    }

    private static HashMap<String, Object> addKeyToHashMap(String key, Object value) {
        HashMap<String, Object> hashmap = new HashMap<>();
        hashmap.put(key, value);
        return hashmap;
    }
    
    private static ArrayList<HashMap<String, Object>> getThemeListPrivate() {
        List<File> files = StorageUtil.getFiles(themes_folder);
        ArrayList<HashMap<String, Object>> themes = new ArrayList<>();
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.registerTypeAdapter(new TypeToken<HashMap<String, Object>>() {
                }.getType(),
                new HashMapDeserializerFix());
        Gson gson = gsonBuilder.create();
        for (File file : files) {
            try {
                themes.add((HashMap<String, Object>) gson.fromJson(StorageUtil
                                .readFile(file.getAbsolutePath()),
                        new TypeToken<HashMap<String, Object>>() {
                        }.getType()));
            } catch (IOException ignored) {
            }
        }

        themes.addAll(0, getDefaultThemes());

        return themes;
    }
  
    private static void initializeData() {
        // Initialize

        // Check if os-thm folder exist
        if (!StorageUtil.isDirectoryExists(exported_themes_folder))
            StorageUtil.createDirectory(exported_themes_folder);

        // Check if .os-thm folder exist
        if (!StorageUtil.isDirectoryExists(themes_folder) ||
                !StorageUtil.isFileExist(config_file)) {
            // Initialize the folder structure
            // Create the folder .osthm and .osthm/themes
            StorageUtil.createDirectory(themes_folder);

            // Create the config file
            StorageUtil.createFile(config_file, "{}");
        } else {
            // Check if the config file is valid or not
            try {
                if (!isJSONValid(StorageUtil.readFile(config_file)))
                    StorageUtil.createFile(config_file, "{}");
            } catch (IOException ignored) {
            }

            // Check if the themes are valid or not
            List<File> files = StorageUtil.getFiles(themes_folder);
            for (File file : files) {
                // If the theme is invalid, then delete it
                try {
                    if (!isJSONValid(StorageUtil.readFile(file.getAbsolutePath())))
                        StorageUtil.deleteFile(file.getAbsolutePath());
                    else {
                        // Detect if there are any filename that doesn't matches the UUID, then fix it
                        HashMap<String, Object> thm = new Gson().fromJson(StorageUtil
                                        .readFile(file.getAbsolutePath()),
                                new TypeToken<HashMap<String, Object>>() {
                                }.getType());

                        if (!(thm.get("uuid").toString() + ".os-thm").equals(file.getName()))
                            StorageUtil.rename(file.getAbsolutePath(), themes_folder +
                                    thm.get("uuid").toString() + ".os-thm");
                    }
                } catch (IOException ignored) {
                }
            }
        }
        
        if (!containsConf("currentTheme"))
            setConf("currentTheme", "default");
    }
    
    public static OsThmTheme getCurrentTheme() {
        initializeData();
        
        ArrayList<String> indexUUID = new ArrayList<>();
        ArrayList<HashMap<String, Object>> metadataarray = getThemeListPrivate();

        for (int i = 0; i < metadataarray.size(); i++)
            indexUUID.add(metadataarray.get(indexUUID.size()).get("uuid").toString());

        return new OsThmTheme((HashMap<String, Integer>)
                new Gson().fromJson(metadataarray.get(indexUUID.indexOf(osthmManager
                                .getConf("currentTheme", "default")))
                                .get("themesjson").toString(),
                        new TypeToken<HashMap<String, Integer>>() {
                        }.getType()));
    }
    
    public static OsThmMetadata getCurrentThemeMetadata() {
        initializeData();
        
        ArrayList<String> indexUUID = new ArrayList<>();
        ArrayList<HashMap<String, Object>> metadataarray = getThemeListPrivate();

        for (int i = 0; i < metadataarray.size(); i++)
            indexUUID.add(metadataarray.get(indexUUID.size()).get("uuid").toString());

        return new OsThmMetadata(metadataarray.get(indexUUID.indexOf(osthmManager
                .getConf("currentTheme", "default"))));
    }
}

public class OsThmTheme {
    public int colorPrimary;
    public int colorPrimaryText;
    public int colorPrimaryDark;
    public int colorStatusbarTint;
    public int colorBackground;
    public int colorBackgroundText;
    public int colorAccent;
    public int colorAccentText;
    public int shadow;
    public int colorControlHighlight;
    public int colorHint;
    public int colorPrimaryTint;
    public int colorBackgroundTint;
    public int colorPrimaryCard;
    public int colorBackgroundCard;
    public int colorPrimaryCardText;
    public int colorBackgroundCardText;
    public int colorPrimaryCardTint;
    public int colorBackgroundCardTint;
    public int colorDialog;
    public int colorDialogText;
    public int colorDialogTint;

    public OsThmTheme() {
        HashMap<String, Integer> data = new Gson().fromJson(DefaultThemes.getDefaultThemes()
                        .get(0).get("themesjson").toString(),
                new TypeToken<HashMap<String, Object>>() {
                }.getType());
        setData(data);
    }

    public OsThmTheme(String json_string) {
        HashMap<String, Integer> json_data = new Gson().fromJson(
                json_string, new TypeToken<HashMap<String, Integer>>() {
                }.getType()
        );

        this.setData(json_data);
    }

    public OsThmTheme(HashMap<String, Integer> data) {
        this.setData(data);
    }

    public OsThmTheme(int colorPrimary, int colorPrimaryText, int colorPrimaryDark, int colorStatusbarTint, int colorBackground, int colorBackgroundText, int colorAccent, int colorAccentText, int shadow, int colorControlHighlight, int colorHint, int colorPrimaryTint, int colorBackgroundTint, int colorPrimaryCard, int colorBackgroundCard, int colorPrimaryCardText, int colorBackgroundCardText, int colorPrimaryCardTint, int colorBackgroundCardTint, int colorDialog, int colorDialogText, int colorDialogTint) {
        this.colorPrimary = colorPrimary;
        this.colorPrimaryText = colorPrimaryText;
        this.colorPrimaryDark = colorPrimaryDark;
        this.colorStatusbarTint = colorStatusbarTint;
        this.colorBackground = colorBackground;
        this.colorBackgroundText = colorBackgroundText;
        this.colorAccent = colorAccent;
        this.colorAccentText = colorAccentText;
        this.shadow = shadow;
        this.colorControlHighlight = colorControlHighlight;
        this.colorHint = colorHint;
        this.colorPrimaryTint = colorPrimaryTint;
        this.colorBackgroundTint = colorBackgroundTint;
        this.colorPrimaryCard = colorPrimaryCard;
        this.colorBackgroundCard = colorBackgroundCard;
        this.colorPrimaryCardText = colorPrimaryCardText;
        this.colorBackgroundCardText = colorBackgroundCardText;
        this.colorPrimaryCardTint = colorPrimaryCardTint;
        this.colorBackgroundCardTint = colorBackgroundCardTint;
        this.colorDialog = colorDialog;
        this.colorDialogText = colorDialogText;
        this.colorDialogTint = colorDialogTint;
    }

    public void setData(HashMap<String, Integer> data) {
        this.colorPrimary = data.get("colorPrimary");
        this.colorPrimaryText = data.get("colorPrimaryText");
        this.colorPrimaryDark = data.get("colorPrimaryDark");
        this.colorStatusbarTint = data.get("colorStatusbarTint");
        this.colorBackground = data.get("colorBackground");
        this.colorBackgroundText = data.get("colorBackgroundText");
        this.colorAccent = data.get("colorAccent");
        this.colorAccentText = data.get("colorAccentText");
        this.shadow = data.get("shadow");
        this.colorControlHighlight = data.get("colorControlHighlight");
        this.colorHint = data.get("colorHint");
        this.colorPrimaryTint = data.get("colorPrimaryTint");
        this.colorBackgroundTint = data.get("colorBackgroundTint");
        this.colorPrimaryCard = data.get("colorPrimaryCard");
        this.colorBackgroundCard = data.get("colorBackgroundCard");
        this.colorPrimaryCardText = data.get("colorPrimaryCardText");
        this.colorBackgroundCardText = data.get("colorBackgroundCardText");
        this.colorPrimaryCardTint = data.get("colorPrimaryCardTint");
        this.colorBackgroundCardTint = data.get("colorBackgroundCardTint");
        this.colorDialog = data.get("colorDialog");
        this.colorDialogText = data.get("colorDialogText");
        this.colorDialogTint = data.get("colorDialogTint");
    }

    public HashMap<String, Integer> toHashMap() {
        HashMap<String, Integer> result = new HashMap<>();
        result.put("colorPrimary", colorPrimary);
        result.put("colorPrimaryText", colorPrimaryText);
        result.put("colorPrimaryDark", colorPrimaryDark);
        result.put("colorStatusbarTint", colorStatusbarTint);
        result.put("colorBackground", colorBackground);
        result.put("colorBackgroundText", colorBackgroundText);
        result.put("colorAccent", colorAccent);
        result.put("colorAccentText", colorAccentText);
        result.put("shadow", shadow);
        result.put("colorControlHighlight", colorControlHighlight);
        result.put("colorHint", colorHint);
        result.put("colorPrimaryTint", colorPrimaryTint);
        result.put("colorBackgroundTint", colorBackgroundTint);
        result.put("colorPrimaryCard", colorPrimaryCard);
        result.put("colorBackgroundCard", colorBackgroundCard);
        result.put("colorPrimaryCardText", colorPrimaryCardText);
        result.put("colorBackgroundCardText", colorBackgroundCardText);
        result.put("colorPrimaryCardTint", colorPrimaryCardTint);
        result.put("colorBackgroundCardTint", colorBackgroundCardTint);
        result.put("colorDialog", colorDialog);
        result.put("colorDialogText", colorDialogText);
        result.put("colorDialogTint", colorDialogTint);
        return result;
    }

    public String toJsonString() {
        /*  // Easier-to-understand Version:
            HashMap<String, Object> result = this.toHashMap();
            Gson gson = new Gson();
            return gson.toJson(result)
         */

        // Less-space version:
        return new Gson().toJson(this.toHashMap());
    }
}
    
public class OsThmMetadata {
    public String themesname;
    public String themesinfo;
    public String themesauthor;
    public int os_thm_version;
    public String uuid;
    public int themeversion;

    public OsThmMetadata(String themesname, String themesinfo, String themesauthor, int os_thm_version, String uuid, int themeversion) {
        this.themesname = themesname;
        this.themesinfo = themesinfo;
        this.themesauthor = themesauthor;
        this.os_thm_version = os_thm_version;
        this.uuid = uuid;
        this.themeversion = themeversion;
    }

    public OsThmMetadata() {
        new OsThmMetadata((HashMap<String, Object>) new Gson().fromJson(DefaultThemes
                        .getDefaultThemes().get(0).get("themesjson").toString(),
                new TypeToken<HashMap<String, Integer>>() {
                }.getType()));
    }

    public OsThmMetadata(HashMap<String, Object> data) {
        themesname = data.get("themesname").toString();
        themesinfo = data.get("themesinfo").toString();
        themesauthor = data.get("themesauthor").toString();
        os_thm_version = (int) data.get("os-thm-version");
        uuid = data.get("uuid").toString();
        themeversion = (int) data.get("theme-version");
    }
}

public class osthmException extends Exception {
    public osthmException(String message) {
        super(message);
    }
}

public class StorageUtil {
    private static final String TAG = "StorageUtil";

    public static boolean isExternalWritable() {
        String state = Environment.getExternalStorageState();
        return Environment.MEDIA_MOUNTED.equals(state);
    }

    public static boolean createDirectory(String path) {
        File directory = new File(path);
        if (directory.exists()) {
            Log.w(TAG, "Directory '" + path + "' already exists");
            return false;
        }
        return directory.mkdirs();
    }

    public static boolean createDirectory(String path, boolean override) {

        // Check if directory exists. If yes, then delete all directory
        if (override && isDirectoryExists(path)) {
            deleteDirectory(path);
        }

        // Create new directory
        return createDirectory(path);
    }

    public static boolean deleteDirectory(String path) {
        return deleteDirectoryImpl(path);
    }

    public static boolean isDirectoryExists(String path) {
        return new File(path).exists();
    }

    public static boolean createFile(String path, String content) {
        return createFile(path, content.getBytes());
    }

    public static boolean createFile(String path, byte[] content) {
        try {
            OutputStream stream = new FileOutputStream(new File(path));

            stream.write(content);
            stream.flush();
            stream.close();
        } catch (IOException e) {
            Log.e(TAG, "Failed create file", e);
            return false;
        }
        return true;
    }

    public static boolean createFile(String path, Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return createFile(path, byteArray);
    }

    public static boolean deleteFile(String path) {
        File file = new File(path);
        return file.delete();
    }

    public static boolean isFileExist(String path) {
        return new File(path).exists();
    }

    // Copied from: https://www.journaldev.com/9400/android-external-storage-read-write-save-file
    public static String readFile(String path) throws IOException {
        StringBuilder output = new StringBuilder();
        FileInputStream fis = new FileInputStream(path);
        DataInputStream in = new DataInputStream(fis);
        BufferedReader br =
                new BufferedReader(new InputStreamReader(in));
        String strLine;
        while ((strLine = br.readLine()) != null) {
            output.append(strLine);
        }
        in.close();
        return output.toString();
    }

    public static void appendFile(String path, String content) {
        appendFile(path, content.getBytes());
    }

    public static void appendFile(String path, byte[] bytes) {
        if (!isFileExist(path)) {
            Log.w(TAG, "Impossible to append content, because such file doesn't exist");
            return;
        }

        try {
            FileOutputStream stream = new FileOutputStream(new File(path), true);
            stream.write(bytes);
            stream.write(System.getProperty("line.separator").getBytes());
            stream.flush();
            stream.close();
        } catch (IOException e) {
            Log.e(TAG, "Failed to append content to file", e);
        }
    }

    public static List<File> getNestedFiles(String path) {
        File file = new File(path);
        List<File> out = new ArrayList<File>();
        getDirectoryFilesImpl(file, out);
        return out;
    }

    public static List<File> getFiles(String dir) {
        return getFiles(dir, null);
    }

    public static List<File> getFiles(String dir, final String matchRegex) {
        File file = new File(dir);
        File[] files = null;
        if (matchRegex != null) {
            FilenameFilter filter = new FilenameFilter() {
                @Override
                public boolean accept(File dir, String fileName) {
                    return fileName.matches(matchRegex);
                }
            };
            files = file.listFiles(filter);
        } else {
            files = file.listFiles();
        }
        return files != null ? Arrays.asList(files) : null;
    }

    public static File getFile(String path) {
        return new File(path);
    }

    public static boolean rename(String fromPath, String toPath) {
        File file = getFile(fromPath);
        File newFile = new File(toPath);
        return file.renameTo(newFile);
    }

    public static boolean copy(String fromPath, String toPath) {
        File file = getFile(fromPath);
        if (!file.isFile()) {
            return false;
        }

        FileInputStream inStream = null;
        FileOutputStream outStream = null;
        try {
            inStream = new FileInputStream(file);
            outStream = new FileOutputStream(new File(toPath));
            FileChannel inChannel = inStream.getChannel();
            FileChannel outChannel = outStream.getChannel();
            inChannel.transferTo(0, inChannel.size(), outChannel);
        } catch (Exception e) {
            Log.e(TAG, "Failed copy", e);
            return false;
        } finally {
            closeSilently(inStream);
            closeSilently(outStream);
        }
        return true;
    }

    public static boolean move(String fromPath, String toPath) {
        if (copy(fromPath, toPath)) {
            return getFile(fromPath).delete();
        }
        return false;
    }

    private static boolean deleteDirectoryImpl(String path) {
        File directory = new File(path);

        // If the directory exists then delete
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files == null) {
                return true;
            }
            // Run on all sub files and folders and delete them
            for (File file : files) {
                if (file.isDirectory()) {
                    deleteDirectoryImpl(file.getAbsolutePath());
                } else {
                    file.delete();
                }
            }
        }
        return directory.delete();
    }

    private static void getDirectoryFilesImpl(File directory, List<File> out) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files == null) {
                return;
            } else {
                for (File file : files) {
                    if (file.isDirectory()) {
                        getDirectoryFilesImpl(file, out);
                    } else {
                        out.add(file);
                    }
                }
            }
        }
    }

    private static void closeSilently(Closeable closeable) {
        if (closeable != null) {
            try {
                closeable.close();
            } catch (IOException ignored) {
            }
        }
    }
}
