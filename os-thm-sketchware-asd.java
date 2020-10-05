// Put this in onCreate
}

public static final String externalDir = Environment.getExternalStorageDirectory().getAbsolutePath();
public static final String themes_folder = externalDir + "/.os-thm/themes/";
public static final String config_file = externalDir + "/.os-thm/conf";
public static final String exported_themes_folder = externalDir + "/os-thm/";

public static final String default_theme = "{\"colorPrimary\":-14575885,\"colorBackgroundCardTint\":-16777216,\"colorPrimaryDark\":-15242838,\"colorBackgroundText\":-16777216,\"colorBackground\":-1,\"shadow\":1,\"colorPrimaryTint\":-1,\"colorHint\":-5723992,\"colorStatusbarTint\":1,\"colorPrimaryCardTint\":-16777216,\"colorAccent\":-720809,\"colorPrimaryText\":-1,\"colorBackgroundCardText\":-16777216,\"colorBackgroundTint\":-16777216,\"colorControlHighlight\":1073741824,\"colorAccentText\":-1,\"colorBackgroundCard\":-1,\"colorPrimaryCardText\":-16777216,\"colorPrimaryCard\":-1,\"colorDialog\":-1,\"colorDialogText\":-16777216,\"colorDialogTint\":-16777216}";
public static final String dark_theme = "{\"colorPrimary\":-13421773,\"colorBackgroundCardTint\":-7762803,\"colorPrimaryDark\":-14540254,\"colorBackgroundText\":-1,\"colorBackground\":-14342875,\"shadow\":1,\"colorDialog\":-13685459,\"colorPrimaryTint\":-1,\"colorHint\":-8355712,\"colorStatusbarTint\":1,\"colorDialogTint\":-7762803,\"colorPrimaryCardTint\":-7762803,\"colorPrimaryText\":-1,\"colorAccent\":-720809,\"colorBackgroundCardText\":-2038811,\"colorBackgroundTint\":-1,\"colorControlHighlight\":1090519039,\"colorAccentText\":-1,\"colorDialogText\":-2038811,\"colorBackgroundCard\":-13685459,\"colorPrimaryCardText\":-2038811,\"colorPrimaryCard\":-13685459}";

public static Theme getCurrentTheme() {
    if (!new java.io.File(exported_themes_folder).exists())
        return new Theme(default_theme);
    
    try {
        org.json.JSONObject conf = new org.json.JSONObject(readFile(config_file));
        String currentTheme = conf.getString("currentTheme");
        if (currentTheme.equals("default")) {
            return new Theme(default_theme);
        } else if (currentTheme.equals("dark")) {
            return new Theme(dark_theme);
        }
        org.json.JSONObject full_theme = new JSONObject(readFile(themes_folder + currentTheme + ".os-thm"));
        return new Theme(full_theme.getString("themesjson"));

    } catch (java.lang.Exception e) {
        return new Theme(default_theme);
    }
}

public static String readFile(String path) throws java.io.IOException {
    java.lang.StringBuilder output = new java.lang.StringBuilder();
    java.io.FileInputStream fis = new java.io.FileInputStream(path);
    java.io.DataInputStream in = new java.io.DataInputStream(fis);
    java.io.BufferedReader br =
            new java.io.BufferedReader(new java.io.InputStreamReader(in));
    String strLine;
    while ((strLine = br.readLine()) != null) {
        output.append(strLine);
    }
    in.close();
    return output.toString();
}

public static class Theme {
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


    public Theme(String json_string) {
        try {
            org.json.JSONObject theme = new org.json.JSONObject(json_string);

            this.colorPrimary = theme.getInt("colorPrimary");
            this.colorPrimaryText = theme.getInt("colorPrimaryText");
            this.colorPrimaryDark = theme.getInt("colorPrimaryDark");
            this.colorStatusbarTint = theme.getInt("colorStatusbarTint");
            this.colorBackground = theme.getInt("colorBackground");
            this.colorBackgroundText = theme.getInt("colorBackgroundText");
            this.colorAccent = theme.getInt("colorAccent");
            this.colorAccentText = theme.getInt("colorAccentText");
            this.shadow = theme.getInt("shadow");
            this.colorControlHighlight = theme.getInt(" colorControlHighlight");
            this.colorHint = theme.getInt(" colorHint");
            this.colorPrimaryTint = theme.getInt(" colorPrimaryTint");
            this.colorBackgroundTint = theme.getInt(" colorBackgroundTint");
            this.colorPrimaryCard = theme.getInt(" colorPrimaryCard");
            this.colorBackgroundCard = theme.getInt(" colorBackgroundCard");
            this.colorPrimaryCardText = theme.getInt(" colorPrimaryCardText");
            this.colorBackgroundCardText = theme.getInt(" colorBackgroundCardText");
            this.colorPrimaryCardTint = theme.getInt(" colorPrimaryCardTint");
            this.colorBackgroundCardTint = theme.getInt(" colorBackgroundCardTint");
            this.colorDialog = theme.getInt(" colorDialog");
            this.colorDialogText = theme.getInt(" colorDialogText");
            this.colorDialogTint = theme.getInt(" colorDialogTint");
        } catch (JSONException e) {
            // ================= Default vanilla colors
            // "{\"colorPrimary\":-14575885,
            //\"colorBackgroundCardTint\":-16777216
            //,\"colorPrimaryDark\":-15242838
            //,\"colorBackgroundText\":-16777216,
            //\"colorBackground\":-1,
            //\"shadow\":1,\
            //"colorPrimaryTint\":-1,
            //\"colorHint\":-5723992,
            //\"colorStatusbarTint\":1,
            //\"colorPrimaryCardTint\":-16777216,
            ///\"colorAccent\":-720809,
            //\"colorPrimaryText\":-1,
            //\"colorBackgroundCardText\":-16777216,
            //\"colorBackgroundTint\":-16777216,
            //"colorControlHighlight\":1073741824,\
            //"colorAccentText\":-1,
            //\"colorBackgroundCard\":-1,
            //\"colorPrimaryCardText\":-16777216,
            //\"colorPrimaryCard\":-1,
            //\"colorDialog\":-1,
            //\"colorDialogText\":-16777216,
            //\"colorDialogTint\":-16777216}";
            this.colorPrimary = -14575885;
            this.colorPrimaryText = -1;
            this.colorPrimaryDark = -15242838;
            this.colorStatusbarTint = 1;
            this.colorBackground = -1;
            this.colorBackgroundText = -16777216;
            this.colorAccent = -720809;
            this.colorAccentText = -1;
            this.shadow = 1;
            this.colorControlHighlight = 1073741824;
            this.colorHint = -5723992;
            this.colorPrimaryTint = -1;
            this.colorBackgroundTint = -16777216;
            this.colorPrimaryCard = -1;
            this.colorBackgroundCard = -1;
            this.colorPrimaryCardText = -16777216;
            this.colorBackgroundCardText = -16777216;
            this.colorPrimaryCardTint = -16777216;
            this.colorBackgroundCardTint = -16777216;
            this.colorDialog = -1;
            this.colorDialogText = -16777216;
            this.colorDialogTint = -16777216;
        }
    }

    public Theme(int colorPrimary, int colorPrimaryText, int colorPrimaryDark, int colorStatusbarTint, int colorBackground, int colorBackgroundText, int colorAccent, int colorAccentText, int shadow, int colorControlHighlight, int colorHint, int colorPrimaryTint, int colorBackgroundTint, int colorPrimaryCard, int colorBackgroundCard, int colorPrimaryCardText, int colorBackgroundCardText, int colorPrimaryCardTint, int colorBackgroundCardTint, int colorDialog, int colorDialogText, int colorDialogTint) {
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
}
