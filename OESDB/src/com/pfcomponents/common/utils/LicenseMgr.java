/*jadclipse*/// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.

package com.pfcomponents.common.utils;

import org.eclipse.swt.widgets.*;

public class LicenseMgr
{

    public LicenseMgr()
    {
    }

    public static void check(Display display)
    {
        if(!initalized)
        {
            Shell shell = new Shell(display);
            int style = 2;
            MessageBox messageBox = new MessageBox(shell, style);
            messageBox.setMessage("PFGrid Evaluation");
            messageBox.open();
        }
        initalized = true;
    }

    private static boolean initalized = true;

}


/*
	DECOMPILATION REPORT

	Decompiled from: C:\Users\mobin\Desktop\pfgrid_swt_eval\lib\com.pfcomponents.common_1.2.0.0.jar
	Total time: 35 ms
	Jad reported messages/errors:
	Exit status: 0
	Caught exceptions:
*/