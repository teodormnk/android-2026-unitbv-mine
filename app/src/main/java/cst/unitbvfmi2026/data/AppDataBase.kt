package cst.unitbvfmi2026.data

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase
import cst.unitbvfmi2026.BuildConfig
import cst.unitbvfmi2026.data.dao.UserDao
import cst.unitbvfmi2026.data.entities.AddressEntity
import cst.unitbvfmi2026.data.entities.UserEntity

@Database(
    entities = [UserEntity::class, AddressEntity::class],
    version = 3
)
abstract class AppDataBase : RoomDatabase() {
    abstract fun userDao(): UserDao

    //in Kotlin NU exista static, dar exista companion object
    companion object {
        @Volatile
        private var instance: AppDataBase? = null
        private val migration_1_2 = object : Migration(1, 2) { //migrare de la ver 1 la 2
            override fun migrate(db: SupportSQLiteDatabase) {
                db.execSQL("ALTER TABLE users ADD COLUMN address_id INTEGER")
                db.execSQL(
                    """CREATE TABLE IF NOT EXISTS addresses(
                    | id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                    | city TEXT NOT NULL,
                    | country TEXT NOT NULL
                    |)""".trimMargin()
                )
            }
        }

        fun getDatabase(context: Context): AppDataBase {
            return instance ?: synchronized(this) { //synchronized = exitare dubla instantiere
                var newInstance = Room.databaseBuilder(
                    context.applicationContext,//contextul
                    AppDataBase::class.java,//clasa
                    "app_database"//numele fisierului din memoria device-ului
                )
                    .addMigrations(migration_1_2)
                if (BuildConfig.DEBUG) {//NU vrem sa fie in productie
                    newInstance.fallbackToDestructiveMigration(false)
                }

                val builtInstance = newInstance.build()
                instance = builtInstance
                builtInstance//returneaza newInstance
            }
        }
    }
    //daca exista mai multe migrari, la update de la 1 la 5, toate migrarile se pun cu "," si se construieste prin migrari: 1 -> 2 -> 3 ...
    //caz dev: nu vrem migrare pt orice chestie mica => folosim fallbackToDestructiveMigration
}