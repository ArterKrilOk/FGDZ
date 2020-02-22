package com.pixelswordgames.fgdz.Parser;

import android.content.Context;

import com.pixelswordgames.fgdz.R;

import java.util.ArrayList;
import java.util.List;

public class Subjects {

    //Subjects
    public final static String ALGEBRA = "algebra";
    public final static String GEOMETRIA = "geometriya";
    public final static String MATH = "matematika";
    public final static String RUSSIAN = "russkiy";
    public final static String ENGLISH = "angliyskiy";
    public final static String PHYSIC = "fizika";
    public final static String CHEMISTRY = "himiya";
    public final static String BIOLOGY = "biologiya";
    public final static String IT = "informatika";
    public final static String UKRAIN = "ukrainskiy_yazik";
    public final static String FRANCH = "francuzskiy_yazik";
    public final static String DEUCH = "deutch";
    public final static String HISTORY = "history";
    public final static String LITRICHA = "literatura";
    public final static String GEOGRAPHY = "geografiya";
    public final static String CHERCHENIE = "cherchenie";
    public final static String ASTRONOMY = "astronomiya";
    public final static String OBJ = "obj";
    public final static String PRIRODOVED = "prirodovedenie";
    public final static String MUSIC = "musica";
    public final static String SOCIAL = "obshhestvoznanie";
    public final static String OKMIR = "okruzhayushchiy_mir";
    public final static String ECOLOGY = "ekologiya";
    public final static String TEHNOLOGY = "tekhnologiya";
    public final static String ART = "iskusstvo";
    public final static String CHINES = "kitayskiy_yazyk";

    public final static String[] MAIN_SUBJECTS = {MATH, RUSSIAN, ALGEBRA, GEOMETRIA, ENGLISH, PHYSIC, CHEMISTRY, BIOLOGY, DEUCH, HISTORY};

    //1 Class
    private final static int[] class1SubNames = {R.string.sub_maths, R.string.sub_en, R.string.sub_ru, R.string.sub_it, R.string.sub_music, R.string.sub_lit, R.string.sub_okmir};
    private final static String[] class1Sub = {MATH, ENGLISH, RUSSIAN, IT, MUSIC, LITRICHA, OKMIR};

    //2 Class
    private final static int[] class2SubNames = {R.string.sub_maths, R.string.sub_en, R.string.sub_ru, R.string.sub_de, R.string.sub_it,R.string.sub_music, R.string.sub_lit, R.string.sub_okmir, R.string.sub_tech};
    private final static String[] class2Sub = {MATH, ENGLISH, RUSSIAN, DEUCH, IT, MUSIC, LITRICHA, OKMIR, TEHNOLOGY};

    //3 Class
    private final static int[] class3SubNames = {R.string.sub_maths, R.string.sub_en, R.string.sub_ru, R.string.sub_de, R.string.sub_it,R.string.sub_music, R.string.sub_lit, R.string.sub_okmir};
    private final static String[] class3Sub = {MATH, ENGLISH, RUSSIAN, DEUCH, IT, MUSIC, LITRICHA, OKMIR};

    //4 Class
    private final static int[] class4SubNames = {R.string.sub_maths, R.string.sub_en, R.string.sub_ru, R.string.sub_de, R.string.sub_it,R.string.sub_music, R.string.sub_lit, R.string.sub_okmir};
    private final static String[] class4Sub = {MATH, ENGLISH, RUSSIAN, DEUCH, IT, MUSIC, LITRICHA, OKMIR};

    //5 Class
    private final static int[] class5SubNames = {R.string.sub_maths, R.string.sub_en, R.string.sub_ru, R.string.sub_de, R.string.sub_uk, R.string.sub_fr, R.string.sub_bio, R.string.sub_history,R.string.sub_it,R.string.sub_obj,R.string.sub_geo, R.string.sub_prirod,R.string.sub_music,R.string.sub_lit,R.string.sub_social,R.string.sub_tech,R.string.sub_ch};
    private final static String[] class5Sub = {MATH, ENGLISH, RUSSIAN, DEUCH, UKRAIN, FRANCH, BIOLOGY,HISTORY, IT, OBJ,GEOGRAPHY, PRIRODOVED, MUSIC, LITRICHA, SOCIAL, TEHNOLOGY, CHINES};

    //6 Class
    private final static int[] class6SubNames = {R.string.sub_maths, R.string.sub_en, R.string.sub_ru,R.string.sub_ph, R.string.sub_de, R.string.sub_uk, R.string.sub_fr, R.string.sub_bio, R.string.sub_history,R.string.sub_it,R.string.sub_obj,R.string.sub_geo, R.string.sub_prirod,R.string.sub_music,R.string.sub_lit,R.string.sub_social,R.string.sub_tech,R.string.sub_ch, R.string.sub_ecology};
    private final static String[] class6Sub = {MATH, ENGLISH, RUSSIAN,PHYSIC, DEUCH, UKRAIN, FRANCH, BIOLOGY,HISTORY, IT, OBJ,GEOGRAPHY, PRIRODOVED, MUSIC, LITRICHA, SOCIAL, TEHNOLOGY, CHINES, ECOLOGY};

    //7 Class
    private final static int[] class7SubNames = {R.string.sub_algebra,R.string.sub_geom,R.string.sub_maths, R.string.sub_en, R.string.sub_ru,R.string.sub_ph,R.string.sub_chem,R.string.sub_cherch, R.string.sub_de, R.string.sub_uk, R.string.sub_fr, R.string.sub_bio, R.string.sub_history,R.string.sub_it,R.string.sub_obj,R.string.sub_geo,R.string.sub_lit,R.string.sub_social,R.string.sub_tech,R.string.sub_ch, R.string.sub_ecology, R.string.sub_art};
    private final static String[] class7Sub = {ALGEBRA,GEOMETRIA,MATH, ENGLISH, RUSSIAN,PHYSIC,CHEMISTRY,CHERCHENIE, DEUCH, UKRAIN, FRANCH, BIOLOGY,HISTORY, IT, OBJ,GEOGRAPHY, LITRICHA, SOCIAL, TEHNOLOGY, CHINES, ECOLOGY, ART};

    //8 Class
    private final static int[] class8SubNames = {R.string.sub_algebra,R.string.sub_geom,R.string.sub_maths, R.string.sub_en, R.string.sub_ru,R.string.sub_ph,R.string.sub_chem,R.string.sub_cherch, R.string.sub_de, R.string.sub_uk, R.string.sub_fr, R.string.sub_bio, R.string.sub_history,R.string.sub_it,R.string.sub_obj,R.string.sub_geo,R.string.sub_lit,R.string.sub_social,R.string.sub_tech,R.string.sub_ch, R.string.sub_ecology, R.string.sub_art};
    private final static String[] class8Sub = {ALGEBRA,GEOMETRIA,MATH, ENGLISH, RUSSIAN,PHYSIC,CHEMISTRY,CHERCHENIE, DEUCH, UKRAIN, FRANCH, BIOLOGY,HISTORY, IT, OBJ,GEOGRAPHY, LITRICHA, SOCIAL, TEHNOLOGY, CHINES, ECOLOGY, ART};

    //9 Class
    private final static int[] class9SubNames = {R.string.sub_algebra,R.string.sub_geom,R.string.sub_maths, R.string.sub_en, R.string.sub_ru,R.string.sub_ph,R.string.sub_chem,R.string.sub_cherch, R.string.sub_de, R.string.sub_uk, R.string.sub_fr, R.string.sub_bio, R.string.sub_history,R.string.sub_it,R.string.sub_obj,R.string.sub_geo,R.string.sub_lit,R.string.sub_social,R.string.sub_tech,R.string.sub_ch, R.string.sub_ecology, R.string.sub_art};
    private final static String[] class9Sub = {ALGEBRA,GEOMETRIA,MATH, ENGLISH, RUSSIAN,PHYSIC,CHEMISTRY,CHERCHENIE, DEUCH, UKRAIN, FRANCH, BIOLOGY,HISTORY, IT, OBJ,GEOGRAPHY, LITRICHA, SOCIAL, TEHNOLOGY, CHINES, ECOLOGY, ART};

    //10 Class
    private final static int[] class10SubNames = {R.string.sub_algebra,R.string.sub_geom,R.string.sub_maths, R.string.sub_en, R.string.sub_ru,R.string.sub_ph,R.string.sub_chem,R.string.sub_cherch, R.string.sub_de, R.string.sub_uk, R.string.sub_fr, R.string.sub_bio, R.string.sub_history,R.string.sub_it,R.string.sub_obj,R.string.sub_geo,R.string.sub_lit,R.string.sub_social};
    private final static String[] class10Sub = {ALGEBRA,GEOMETRIA,MATH, ENGLISH, RUSSIAN,PHYSIC,CHEMISTRY,CHERCHENIE, DEUCH, UKRAIN, FRANCH, BIOLOGY,HISTORY, IT, OBJ,GEOGRAPHY, LITRICHA, SOCIAL};

    //11 Class
    private final static int[] class11SubNames = {R.string.sub_algebra,R.string.sub_geom,R.string.sub_maths, R.string.sub_en, R.string.sub_ru,R.string.sub_ph,R.string.sub_chem,R.string.sub_cherch, R.string.sub_de, R.string.sub_uk, R.string.sub_fr, R.string.sub_bio, R.string.sub_history,R.string.sub_it,R.string.sub_obj,R.string.sub_geo,R.string.sub_lit,R.string.sub_social, R.string.sub_astr};
    private final static String[] class11Sub = {ALGEBRA,GEOMETRIA,MATH, ENGLISH, RUSSIAN,PHYSIC,CHEMISTRY,CHERCHENIE, DEUCH, UKRAIN, FRANCH, BIOLOGY,HISTORY, IT, OBJ,GEOGRAPHY, LITRICHA, SOCIAL, ASTRONOMY};


    private Context context;
    private int curClass;
    private String curSub;

    public Subjects(Context context){
        this.context = context;
        curClass = 1;
        curSub = RUSSIAN;
    }


    private String[] getStringsByIds(int[] ids){
        String[] s = new String[ids.length];

        for(int i = 0; i < ids.length; ++i){
            s[i] = context.getString(ids[i]);
        }

        return s;
    }

    public String[] getSubNames(){
        switch (curClass){
            case 1:
                return getStringsByIds(class1SubNames);
            case 2:
                return getStringsByIds(class2SubNames);
            case 3:
                return getStringsByIds(class3SubNames);
            case 4:
                return getStringsByIds(class4SubNames);
            case 5:
                return getStringsByIds(class5SubNames);
            case 6:
                return getStringsByIds(class6SubNames);
            case 7:
                return getStringsByIds(class7SubNames);
            case 8:
                return getStringsByIds(class8SubNames);
            case 9:
                return getStringsByIds(class9SubNames);
            case 10:
                return getStringsByIds(class10SubNames);
            case 11:
                return getStringsByIds(class11SubNames);
        }
        return getStringsByIds(class1SubNames);
    }


    public void setCurClass(int curClass) {
        this.curClass = curClass;
    }

    public void setCurSub(String curSub) {
        this.curSub = curSub;
    }

    public int getCurClass() {
        return curClass;
    }

    public String getCurSub() {
        return curSub;
    }

}
