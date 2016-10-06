package util;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.drawable.Drawable;

import com.my_travels.br.mytravels.R;


public class Alert {
    private Context contexto;

    public Alert(Context contexto) {
        this.contexto = contexto;
    }


    public void exibirMensagem(String titulo, String mensagem) {
        AlertDialog.Builder dialogo = new AlertDialog.Builder(this.contexto);
        int imageResource = R.drawable.ic_launcher;
        Drawable image = contexto.getResources().getDrawable(imageResource);
        dialogo.setTitle(titulo);
        dialogo.setMessage(mensagem);
        dialogo.setIcon(image);
        dialogo.setNeutralButton("OK", null);
        dialogo.show();
    }


    public static void  exibirMensagemConfirmacaoConclusao(Context contexto, String titulo,
                                                          String mensagem, String txtBotaoPositivo, String txtBotaoNegativo,
                                                          boolean ehCancelavel, final IAlertaConfirmacao alvo) {

        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);




        builder.setTitle(titulo)
                .setMessage(mensagem)
                .setCancelable(false)
                .setPositiveButton(txtBotaoPositivo,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alvo.metodoPositivo(dialog, id);
                            }


                        });

        AlertDialog alert = builder.create();
        alert.setCancelable(ehCancelavel);
        alert.show();
        if (ehCancelavel) {
            alert.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface arg0) {
                    alvo.metodoNegativo(null, 0);
                }
            });
        }
    }
    public static void exibirMensagemConfirmacao(Context contexto, String titulo,
                                                 String mensagem,String txtBotaoNegativo, String txtBotaoPositivo,
                                                 boolean ehCancelavel, final IAlertaConfirmacao alvo) {

        AlertDialog.Builder builder = new AlertDialog.Builder(contexto);

        int imageResource = R.drawable.ic_launcher;
        Drawable image = contexto.getResources().getDrawable(imageResource);

        builder.setTitle(titulo)
                .setMessage(mensagem)
                .setIcon(image)
                .setCancelable(true)
                .setPositiveButton(txtBotaoPositivo,
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                alvo.metodoPositivo(dialog, id);
                            }
                        })
                .setNegativeButton(txtBotaoNegativo,
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int id) {
                                alvo.metodoNegativo(dialog, id);
                            }
                        });

        AlertDialog alert = builder.create();
        alert.setCancelable(ehCancelavel);
        alert.show();
        if (ehCancelavel) {
            alert.setOnCancelListener(new DialogInterface.OnCancelListener() {

                @Override
                public void onCancel(DialogInterface arg0) {
                    alvo.metodoNegativo(null, 0);
                }
            });
        }
    }



}
