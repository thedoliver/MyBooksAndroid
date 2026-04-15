package com.example.mybooks.repository

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.mybooks.entity.BookEntity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@Database(entities = [BookEntity::class], version = 1) // Define as entidades e a versão do banco de dados eu passo um lista de entidades
abstract class BookDatabase: RoomDatabase() {

    abstract fun bookDAO(): BookDAO




    companion object {
        private lateinit var instance: BookDatabase
        private const val DATABASE_NAME = "books_db"


        fun getDatabase(context: Context): BookDatabase {
            if (!::instance.isInitialized) {
                synchronized(this){
                    instance = Room.databaseBuilder(context, BookDatabase::class.java, DATABASE_NAME)
                        .addMigrations(Migrations.migrationFromV1ToV2)
                        /**
                         * .allowMainThreadQueries()  Permite consultas na thread principal
                         * sem ele retorna IllegalStateExpection
                         * Connot acess database on the main thread since it may potentianlly lock th UI for a long period of time
                         * usamos o allowMainThreadQueries() por que ele é sincrono
                         *a HomeFragement chama HomeViewMode que chama o BookRepository que chama o BookDAO que chama BookDatabase
                         *
                         * na homeFragmento eu instancio a HomeViewmodel de inicio quando ele é chamado faz o HomeViewModel.init
                         * que faz o LoadInitialData() no repositorio e ela esta instanciada
                         * volta para HomeFragment e faz o OnResume que faz o homeviewModel.getAllBooks que chama p Repositorio na DAO tem os comandos
                         * de consulas que conectada no banco usando BookDataBase
                         *
                         * O Room por padrão nao permite consultas na Thread principal, PQ?
                         * Quando tempo uma consulta longa ... que demora 5s ele entra no caminho que demora ele retorna ANR ApplicationNotResponding
                         * Isso efeta algo crucial UIX - 1 - Não bloqueio o usuario ele espera reposta imediata
                         * E a resposta esta na Main Thread
                         * Tempo uma sequencia sincrona
                         * Internet -> Dispostivios
                         * Dispositivo -> Internet pode demorar  2.5 segundo -> Responde
                         * ja impacta o usuario
                         * Mantra Garantir respostas ao Usuario
                         * 5G falhou, Hardware Falhou,
                         *
                         *
                         * E temos que optar por processamento Assincrono/ Paralelo/ Concorrente
                         *
                         * allowMainThreadQueries() ele não é um problema ate que ele se torne um problema
                         * temos apenas 20 dados e com 2000 dados 15 dados é inviavel essa consulta e vai crashar
                         * em menos de 5 segundos
                         * Temos que ter consultas assicronas
                         *
                         * Como remover a  allowMainThreadQueries()?
                         *   Temos que ter consultas assicronas
                         *
                         *   1 - addCallBack
                         *   2 - Cria uma classe DatabaseCallBack que overwrite no OnCreate
                         *   quando criar meus dados eu crio junto meu dadds
                         *   mas mesmo assim o onCreate nao consegue rodar sincrono
                         *   3 - Então temos que usar o Coroutines(assincrono)
                         *   CoroutineScope(Dispatchers.IO).launch {
                         *                 instance.bookDAO().insert(getInitialBooks())
                         *             }
                         *   Temos que entender ITEMS que RODEM em PARALELO
                         *  4 - Entendondo o Fluxo eu entendo o problema
                         *  alem da Coroutine tem o metodo getDatabase que faz instancia e tambem garante que ele
                         *  nunca seja nulo
                         *  Criei um novo scope e minha coroutina executa
                         *
                         */
                        .addCallback(DatabaseCallBack(context))
                        //.allowMainThreadQueries()
                        .build()
                    }
                }
                return instance
            }
        }

    private object Migrations {
        val migrationFromV1ToV2: Migration = object : Migration(1, 2) {

            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("DELETE FROM Books")
            }
        }
    }

    private class DatabaseCallBack(val context: Context): Callback(){
        override fun onCreate(db: SupportSQLiteDatabase) {
            super.onCreate(db)
            CoroutineScope(Dispatchers.IO).launch {
                getDatabase(context).bookDAO().insert(getInitialBooks())
            }

        }
        private fun getInitialBooks(): List<BookEntity> {
            return listOf(
                BookEntity(1, "To Kill a Mockingbird", "Harper Lee", true, "Ficção"),
                BookEntity(2, "Dom Casmurro", "Machado de Assis", false, "Romance"),
                BookEntity(3, "O Hobbit", "J.R.R. Tolkien", true, "Fantasia"),
                BookEntity(4, "Cem Anos de Solidão", "Gabriel García Márquez", false, "Romance"),
                BookEntity(5, "O Pequeno Príncipe", "Antoine de Saint-Exupéry", false, "Fantasia"),
                BookEntity(6, "Crime e Castigo", "Fiódor Dostoiévski", false, "Ficção policial"),
                BookEntity(7, "Frankenstein", "Mary Shelley", false, "Terror"),
                BookEntity(8, "Harry Potter e a Pedra Filosofal", "J.K. Rowling", false, "Fantasia"),
                BookEntity(9, "Neuromancer", "William Gibson", false, "Cyberpunk"),
                BookEntity(10, "Senhor dos Anéis", "J.R.R. Tolkien", false, "Fantasia"),
                BookEntity(11, "Drácula", "Bram Stoker", false, "Terror"),
                BookEntity(12, "Orgulho e Preconceito", "Jane Austen", false, "Romance"),
                BookEntity(13, "Harry Potter e a Câmara Secreta", "J.K. Rowling", false, "Fantasia"),
                BookEntity(14, "As Crônicas de Nárnia", "C.S. Lewis", false, "Fantasia"),
                BookEntity(15, "O Código Da Vinci", "Dan Brown", false, "Mistério"),
                BookEntity(16, "It: A Coisa", "Stephen King", false, "Terror"),
                BookEntity(17, "Moby Dick", "Herman Melville", true, "Aventura"),
                BookEntity(18, "O Nome do Vento", "Patrick Rothfuss", true, "Fantasia"),
                BookEntity(19, "O Conde de Monte Cristo", "Alexandre Dumas", true, "Aventura"),
                BookEntity(20, "Os Miseráveis", "Victor Hugo", false, "Romance")
            )

        }
    }


}
