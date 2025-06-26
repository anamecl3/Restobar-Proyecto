package com.cibertec.reztrov01

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.cibertec.reztrov01.models.Reserva
import com.cibertec.reztrov01.models.Restaurant

class ReztroDB(context: Context) : SQLiteOpenHelper(context, "ReztroDB", null, 1) {

    override fun onCreate(db: SQLiteDatabase) {
        val createRestauranteTable ="""
            CREATE TABLE tbRestaurante (
            
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nombre TEXT,
            rating REAL,
            imagen INTEGER,
            distrito TEXT,
            tipo TEXT
            );
        """.trimIndent()
        val createTable = """
            CREATE TABLE tbUsuario (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT,
                correo TEXT,
                clave TEXT,
                dni TEXT
            );
        """.trimIndent()

        val createReservaTable = """
        CREATE TABLE tbReserva (
            id INTEGER PRIMARY KEY AUTOINCREMENT,
            nomUsuario TEXT,
            dni TEXT,
            restaurante TEXT,
            fecha TEXT,
            horario TEXT,
            mesa TEXT
        );
    """.trimIndent()
        db.execSQL(createRestauranteTable)
        db.execSQL(createTable)
        db.execSQL(createReservaTable)

        val restaurantes = listOf(
            Restaurant(0, "La Leña", 4.5, R.drawable.burger1, "Miraflores", "pollos"),
            Restaurant(0, "Pardos", 4.7, R.drawable.rokys, "Surco", "pollos"),
            Restaurant(0, "Trattoria Mamma", 4.4, R.drawable.trattoria1, "Barranco", "trattoria"),
            Restaurant(0, "Pasta Brava", 4.3, R.drawable.trattoria2, "San Isidro", "trattoria"),
            Restaurant(0, "Maido", 4.9, R.drawable.nikkei1, "Surco", "nikkei"),
            Restaurant(0, "Osaka", 4.8, R.drawable.nikkei2, "Barranco", "nikkei")
        )

        for (restaurante in restaurantes) {
            val values = ContentValues().apply {
                put("nombre", restaurante.nombre)
                put("rating", restaurante.rating)
                put("imagen", restaurante.imagen)
                put("distrito", restaurante.distrito)
                put("tipo", restaurante.tipo)
            }
            db.insert("tbRestaurante", null, values)
        }
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        db.execSQL("DROP TABLE IF EXISTS tbUsuario")
        onCreate(db)
    }
    fun existeDni(dni: String): Boolean {
        val cursor = readableDatabase.rawQuery("SELECT * FROM tbUsuario WHERE dni = ?", arrayOf(dni))
        val existe = cursor.count > 0
        cursor.close()
        return existe
    }


    fun existeReserva(reserva: Reserva): Boolean {
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM tbReserva WHERE dni = ? AND restaurante = ? AND fecha = ? AND horario = ?",
            arrayOf(reserva.dni, reserva.restaurante, reserva.fecha, reserva.horario)
        )
        val existe = cursor.count > 0
        cursor.close()
        return existe
    }


    fun insertar(usuario: Usuario): Long {
        val valores = ContentValues().apply {
            put("nombre", usuario.nombre)
            put("correo", usuario.correo)
            put("clave", usuario.clave)
            put("dni", usuario.dni)
        }

        return writableDatabase.insert("tbUsuario", null, valores)
    }

    fun listar(): List<Usuario>{
        val lstusuarios = mutableListOf<Usuario>()
        val cursor = readableDatabase.rawQuery("SELECT * FROM tbUsuario", null)
        if(cursor.moveToFirst()){
            do{
                lstusuarios.add(
                    Usuario(
                        cursor.getInt(0),
                        cursor.getString(1),
                        cursor.getString(2),
                        cursor.getString(3),
                        cursor.getString(4)
                    )
                )
            }while(cursor.moveToNext())
        }

        cursor.close()
        return lstusuarios
    }

    fun eliminar(id: Int): Int {
        return writableDatabase.delete("tbUsuario", "id = ?", arrayOf(id.toString()))
    }

    fun actualizar(usuario: Usuario): Int{
        val valores = ContentValues().apply {
            put("nombre", usuario.nombre)
            put("correo", usuario.correo)
            put("clave", usuario.clave)
            put("dni", usuario.dni)

        }
        return writableDatabase.update("tbUsuario", valores, "id = ?", arrayOf(usuario.codigo.toString()))

    }


    fun existeCorreo(correo: String) : Boolean{
        val cursor = readableDatabase.rawQuery("SELECT * FROM tbUsuario WHERE correo = ?", arrayOf(correo))
        val existe = cursor.count > 0
        cursor.close()
        return existe
    }

    fun validarCredenciales(correo: String, clave: String): Boolean {
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM tbUsuario WHERE correo = ? AND clave = ?",
            arrayOf(correo, clave)
        )
        val valido = cursor.count > 0
        cursor.close()
        return valido
    }

    fun obtenerUsuarioPorCredenciales(correo: String, clave: String): Usuario? {
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM tbUsuario WHERE correo = ? AND clave = ?",
            arrayOf(correo, clave)
        )

        var usuario: Usuario? = null
        if (cursor.moveToFirst()) {
            usuario = Usuario(
                codigo = cursor.getInt(0),
                nombre = cursor.getString(1),
                correo = cursor.getString(2),
                clave = cursor.getString(3),
                dni = cursor.getString(4)
            )
        }

        cursor.close()
        return usuario
    }

    fun obtenerUsuarioPorId(id: Int): Usuario? {
        val cursor = readableDatabase.rawQuery("SELECT * FROM tbUsuario WHERE id = ?", arrayOf(id.toString()))
        var usuario: Usuario? = null

        if (cursor.moveToFirst()) {
            usuario = Usuario(
                codigo = cursor.getInt(0),
                nombre = cursor.getString(1),
                correo = cursor.getString(2),
                clave = cursor.getString(3),
                dni = cursor.getString(4)
            )
        }

        cursor.close()
        return usuario
    }


    fun registrarReserva(reserva: Reserva): Boolean {
        return try {
            val valores = ContentValues().apply {
                put("nomUsuario", reserva.nomUsuario)
                put("dni", reserva.dni)
                put("restaurante", reserva.restaurante)
                put("fecha", reserva.fecha)
                put("horario", reserva.horario)
                put("mesa", reserva.mesa)
            }
            writableDatabase.insert("tbReserva", null, valores) > 0
        } catch (error: Exception) {
            error.printStackTrace()
            false
        }
    }

    //temporal
    fun insertarRestaurant(restaurant: Restaurant): Long {
        val values = ContentValues().apply {
            put("nombre", restaurant.nombre)
            put("rating", restaurant.rating)
            put("imagen", restaurant.imagen)
            put("distrito", restaurant.distrito)
            put("tipo", restaurant.tipo)
        }
        return writableDatabase.insert("tbRestaurant", null, values)
    }
//================================================================\\
    fun listarRestaurantesPorTipo(tipo: String): List<Restaurant> {
        val lista = mutableListOf<Restaurant>()
        val cursor = readableDatabase.rawQuery(
            "SELECT * FROM tbRestaurante WHERE tipo = ?", arrayOf(tipo)
        )

        if (cursor.moveToFirst()) {
            do {
                lista.add(
                    Restaurant(
                        id = cursor.getInt(0),
                        nombre = cursor.getString(1),
                        rating = cursor.getDouble(2),
                        imagen = cursor.getInt(3),
                        distrito = cursor.getString(4),
                        tipo = cursor.getString(5)
                    )
                )
            } while (cursor.moveToNext())
        }
        cursor.close()
        return lista
    }


}
