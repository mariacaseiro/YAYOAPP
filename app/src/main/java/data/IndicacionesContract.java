package data;

import android.provider.BaseColumns;

public class IndicacionesContract {

    public static abstract class IndicacionEntry implements BaseColumns {

        public static final String TABLE_NAME ="indicaciones";

        public static final String ID = "id";
        public static final String RUTA = "ruta";
        public static final String IMAGEN = "imagen";
        public static final String TEXTO_INDICACION = "textoIndicacion";
        public static final String PASOS = "pasos";
        public static final String ORDEN = "orden";

    }
}
