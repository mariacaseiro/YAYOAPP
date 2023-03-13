package data;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;


public class IndicacionesDbHelper extends SQLiteOpenHelper {

    public static final int DATABASE_VERSION = 6;
    public static final String DATABASE_NAME = "BaseDatos.db";

    public IndicacionesDbHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {

        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+IndicacionesContract.IndicacionEntry.TABLE_NAME);

        //--------------------------CREACION DE TABLAS-----------------------------------------------
        //INDICACIONES (Utilizada en el Mapa)
        sqLiteDatabase.execSQL("CREATE TABLE " + IndicacionesContract.IndicacionEntry.TABLE_NAME + " ("
                + IndicacionesContract.IndicacionEntry._ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + IndicacionesContract.IndicacionEntry.ID + " TEXT NOT NULL,"
                + IndicacionesContract.IndicacionEntry.RUTA + " TEXT NOT NULL,"
                + IndicacionesContract.IndicacionEntry.IMAGEN + " TEXT,"
                + IndicacionesContract.IndicacionEntry.TEXTO_INDICACION + " TEXT NOT NULL,"
                + IndicacionesContract.IndicacionEntry.PASOS + " INTEGER NOT NULL,"
                + IndicacionesContract.IndicacionEntry.ORDEN+ " INTEGER NOT NULL,"
                + "UNIQUE (" + IndicacionesContract.IndicacionEntry.ID + "))");


        //--------------------------CARGA DE DATOS-------------------------
        cargarDatos(sqLiteDatabase);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+IndicacionesContract.IndicacionEntry.TABLE_NAME);
        onCreate(db);
    }


    public long nuevaIndicacion(SQLiteDatabase db, Indicacion indicacion) {
        return db.insert(
                IndicacionesContract.IndicacionEntry.TABLE_NAME,
                null,
                indicacion.toContentValues());
    }

    //----------METODOS ASOCIADOS A LA TABLA INDICACIONES----------------------------------------
    private void cargarDatos(SQLiteDatabase db){
        //RUTA: CLASE-BANHO
        nuevaIndicacion(db,new Indicacion("Clase_Banho","salida_clase1","Sal de la clase",2,1));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","salida_clase2","Sal de la clase",1,2));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","salida_clase3","Gira a la izquierda",2,3));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho1","Camina recto",1,4));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho2","Camina recto",2,5));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho3","Camina recto",1,6));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho4","Camina recto",2,7));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho5","Camina recto",1,8));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho6","Camina recto",2,9));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho7","Camina recto",1,10));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho8","Camina recto",2,11));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho9","Camina recto",1,12));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho10","Camina recto",2,13));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho11","Camina recto",1,14));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho12","Camina recto",2,15));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho13","Camina recto",1,16));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho14","Camina recto",2,17));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho15","Camina recto",1,18));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho16","Camina recto",2,19));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho17","Camina recto",1,20));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho18","Camina recto",2,21));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho19","Camina recto",1,22));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho20","Camina recto",2,23));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho21","Camina recto",1,24));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho22","Camina recto",2,25));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho23","Camina recto",1,26));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho24","Camina recto",2,27));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho25","Camina recto",1,28));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho26","Camina recto",2,29));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho27","Camina recto",1,30));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho28","Camina recto",2,31));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho29","Camina recto",1,32));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho30","Camina recto",2,33));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho31","Camina recto",1,34));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","clase_banho32","Gira a la derecha",1,35));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","banho1","Gira a la derecha",2,36));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","banho2","Gira a la derecha",1,37));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","banho3","Gira a la derecha",2,38));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","banho_destino1","Sigue recto",1,39));
        nuevaIndicacion(db,new Indicacion("Clase_Banho","banho_destino2","Llegaste a tu destino",2,40));

        //RUTA: BANHO-CLASE
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase1","Gira a la izquierda",2,1));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase2","Camina recto",2,2));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase3","Camina recto",2,3));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase4","Camina recto",2,4));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase5","Camina recto",1,5));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase6","Camina recto",1,6));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase7","Camina recto",1,7));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase8","Camina recto",1,8));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase9","Camina recto",1,9));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase10","Camina recto",1,10));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase11","Camina recto",1,11));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase12","Camina recto",1,12));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase13","Camina recto",1,13));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase14","Camina recto",1,14));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase15","Camina recto",1,15));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase16","Camina recto",1,16));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase17","Camina recto",1,17));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase18","Camina recto",1,18));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase19","Camina recto",1,19));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase20","Camina recto",1,20));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase21","Camina recto",1,21));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase22","Camina recto",1,22));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase23","Camina recto",1,23));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase24","Camina recto",1,24));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase25","Camina recto",1,25));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase26","Camina recto",1,26));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase27","Camina recto",1,27));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase28","Camina recto",1,28));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase29","Camina recto",1,29));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase30","Camina recto",1,30));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase31","Camina recto",1,31));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","banho_clase32","Gira a la derecha",1,32));
        nuevaIndicacion(db,new Indicacion("Banho_Clase","aula_3_3","Llegaste a tu destino",1,33));

        //RUTA: CLASE-ESCALERAS_EXTERIORES
        nuevaIndicacion(db,new Indicacion("Clase_EscalerasExteriores","salida_clase1","Sal de la clase",2,1));
        nuevaIndicacion(db,new Indicacion("Clase_EscalerasExteriores","salida_clase2","Sal de la clase",1,2));
        nuevaIndicacion(db,new Indicacion("Clase_EscalerasExteriores","salida_clase3","Gira a la derecha",2,3));
        nuevaIndicacion(db,new Indicacion("Clase_EscalerasExteriores","clase_escaleras1","Camina recto",1,4));
        nuevaIndicacion(db,new Indicacion("Clase_EscalerasExteriores","clase_escaleras2","Camina recto",1,5));
        nuevaIndicacion(db,new Indicacion("Clase_EscalerasExteriores","clase_escaleras2","Camina recto",1,6));
        nuevaIndicacion(db,new Indicacion("Clase_EscalerasExteriores","clase_escaleras3","Camina recto",1,7));
        nuevaIndicacion(db,new Indicacion("Clase_EscalerasExteriores","clase_escaleras4","Camina recto",1,8));
        nuevaIndicacion(db,new Indicacion("Clase_EscalerasExteriores","clase_escaleras5","Camina recto",1,9));
        nuevaIndicacion(db,new Indicacion("Clase_EscalerasExteriores","clase_escaleras6","Camina recto",1,10));
        nuevaIndicacion(db,new Indicacion("Clase_EscalerasExteriores","clase_escaleras7","Camina recto",1,11));
        nuevaIndicacion(db,new Indicacion("Clase_EscalerasExteriores","clase_escaleras8","Camina recto",1,12));
        nuevaIndicacion(db,new Indicacion("Clase_EscalerasExteriores","clase_escaleras9","Camina recto",1,13));
        nuevaIndicacion(db,new Indicacion("Clase_EscalerasExteriores","clase_escaleras10","Gira ligeramente a la derecha",1,14));
        nuevaIndicacion(db,new Indicacion("Clase_EscalerasExteriores","clase_escaleras11","Gira ligeramente a la derecha",1,15));
        nuevaIndicacion(db,new Indicacion("Clase_EscalerasExteriores","clase_escaleras12","Cruza la puerta",1,16));
        nuevaIndicacion(db,new Indicacion("Clase_EscalerasExteriores","escaleras_exteriores1","Gira a la derecha",1,17));
        nuevaIndicacion(db,new Indicacion("Clase_EscalerasExteriores","escaleras_exteriores3","Llegaste a tu destino",2,18));

    }

    public ArrayList<Indicacion> obtenerRuta(String nombreRuta){

        //Abrimos la base de datos para lectura
        SQLiteDatabase dbReader =getReadableDatabase();

        //ArrayList para almacenar la ruta leida
        ArrayList<Indicacion> ruta = new ArrayList<>();

        if(dbReader!=null){
            String consulta = "SELECT * FROM indicaciones WHERE ruta = '"+nombreRuta +"' ORDER BY orden;";
            System.out.println(consulta);
            Cursor cursorRuta = dbReader.rawQuery(consulta,null);
            System.out.println("Cursor creado correctamente");
            //Mientras existan elementos en el cursor
            while(cursorRuta.moveToNext()){
                //Posicion 0 = id
                //Posicion 1 = ruta
                //Posicion 2 = imagen
                //Posicion 3 = textoIndicacion
                //Posicion 4 = pasos
                //Posicion 5 = orden
                Indicacion indicacion = new Indicacion(cursorRuta.getString(2),
                        cursorRuta.getString(3),
                        cursorRuta.getString(4),
                        cursorRuta.getInt(5),
                        cursorRuta.getInt(6));

                System.out.println(indicacion.toString());
                ruta.add(indicacion);

                System.out.println("Indicacion "+indicacion.getOrden()+" añadida correctamente");
            }
            //Cerramos los cursores
            cursorRuta.close();
            dbReader.close();
        }

        return ruta;

    }


}
