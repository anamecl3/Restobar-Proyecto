package com.cibertec.reztrov01

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
class UsuarioFragment : Fragment() {

    private lateinit var etNombreUsuario: EditText
    private lateinit var etCorreoUsuario: EditText
    private lateinit var etClaveUsuario: EditText
    private lateinit var etConfirmarClave: EditText
    private lateinit var etDniUsuario: EditText
    private lateinit var btnCrearCuenta: Button
    private lateinit var reztroDB: ReztroDB

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_usuario, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        // Inicializa la base de datos
        reztroDB = ReztroDB(requireContext())

        etNombreUsuario = view.findViewById(R.id.etNombreRegistro)
        etCorreoUsuario = view.findViewById(R.id.etCorreoRegistro)
        etClaveUsuario = view.findViewById(R.id.etPasswordRegistro)
        etConfirmarClave = view.findViewById(R.id.etConfirmarPassword)
        etDniUsuario = view.findViewById(R.id.etDni)
        btnCrearCuenta = view.findViewById(R.id.btnRegistrarUsuario)

        val tvIniciarSesion = view.findViewById<TextView>(R.id.tvIniciarSesion)
        tvIniciarSesion.setOnClickListener {
            val intent = Intent(requireContext(), MainActivity::class.java)
            startActivity(intent)
        }

        btnCrearCuenta.setOnClickListener {
            val nombre = etNombreUsuario.text.toString().trim()
            val correo = etCorreoUsuario.text.toString().trim()
            val clave = etClaveUsuario.text.toString()
            val confirmar = etConfirmarClave.text.toString()
            val dni = etDniUsuario.text.toString()

            when {
                nombre.isEmpty() || correo.isEmpty() || clave.isEmpty() || confirmar.isEmpty() || dni.isEmpty() -> {
                    Toast.makeText(requireContext(), "Completa todos los campos", Toast.LENGTH_SHORT).show()
                }
                !correo.contains("@") -> {
                    Toast.makeText(requireContext(), "Correo inválido", Toast.LENGTH_SHORT).show()
                }
                clave.length < 6 -> {
                    Toast.makeText(requireContext(), "La clave debe tener al menos 6 caracteres", Toast.LENGTH_SHORT).show()
                }
                clave != confirmar -> {
                    Toast.makeText(requireContext(), "Las contraseñas no coinciden", Toast.LENGTH_SHORT).show()
                }

                reztroDB.existeCorreo(correo) -> {
                    Toast.makeText(requireContext(), "Este correo ya está registrado", Toast.LENGTH_SHORT).show()
                }
                reztroDB.existeDni(dni) -> {
                    Toast.makeText(requireContext(), "Este DNI ya está registrado", Toast.LENGTH_SHORT).show()
                }
                else -> {
                    // Crear objeto Usuario
                    val nuevoUsuario = Usuario(
                        nombre = nombre,
                        correo = correo,
                        clave = clave,
                        dni = dni
                    )
                    val resultado = reztroDB.insertar(nuevoUsuario)

                    if (resultado > 0) {
                        mostrarDialogoExito()
                    } else {
                        Toast.makeText(requireContext(), "Error al registrar usuario", Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }

    private fun mostrarDialogoExito() {
        val vista = layoutInflater.inflate(R.layout.dialogo_registro_exito, null)
        val btnAceptar = vista.findViewById<Button>(R.id.btnAceptar)
        val mensaje = vista.findViewById<TextView>(R.id.tvMensajeDialogo)

        mensaje.text = getString(R.string.registro_exitoso)

        val dialogo = AlertDialog.Builder(requireContext())
            .setView(vista)
            .setCancelable(false)
            .create()

        dialogo.show()

        btnAceptar.setOnClickListener {
            dialogo.dismiss()
            Toast.makeText(requireContext(), getString(R.string.toast_registro_completo), Toast.LENGTH_SHORT).show()
            requireActivity().finish()
        }
    }
}
