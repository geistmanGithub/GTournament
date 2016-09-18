package com.geistman.gtournament;

import android.provider.BaseColumns;

public class Player implements BaseColumns{
    public final static String TABLE_NAME = "players";
    public final static String COLUMN_Name = "name";
    public static final String COLUMN_TT_ELO = "TT_ELO";
    public static final String TABLE_ELO_HISTORY = "ELO_History";
}
