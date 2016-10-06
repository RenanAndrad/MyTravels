package util;

import android.content.DialogInterface;


public interface IAlertaConfirmacao {

    public abstract void metodoPositivo(DialogInterface dialog, int id);

    public abstract void metodoNegativo(DialogInterface dialog, int id);
}