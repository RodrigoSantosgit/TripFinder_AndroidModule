package pt.ua.tripfinder_android;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Database(entities = {Trips.class}, version = 1, exportSchema = false)
public abstract class TripRoomDatabase extends RoomDatabase {

    public abstract TripsDao tripsDao();

    private static volatile TripRoomDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static TripRoomDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (TripRoomDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            TripRoomDatabase.class, "trip_database")
                            .addCallback(sTripDatabaseCallback)
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sTripDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(() -> {
                // Populate the database in the background.
                // If you want to start with more words, just add them.
                TripsDao dao = INSTANCE.tripsDao();
                dao.deleteAll();

                List<Trips> lTrips = new ArrayList<>();
                lTrips.add(new Trips(1, 15,"Ria de Aveiro",
                        "Passeio ao longo da Ria de Aveiro",
                        "Passeio ao longo da Ria de Aveiro, desfrute desta maravilhosa experi??ncia passando por diversos locais de interesse em Aveiro como o F??rum e a Pra??a do Peixe.",
                        "https://i2.wp.com/www.portugalnummapa.com/wp-content/uploads/2015/02/moliceiros-na-ria-de-aveiro-e1424799989448.jpg?fit=700%2C498&ssl=1",
                        "Aveiro",
                        40.641482,
                        -8.653080
                ));

                lTrips.add(new Trips(2, 10, "Salinas",
                                "Visita ??s Salinas de Aveiro",
                                "Passeio pela Rua da pego ao longo da Ria, a culminar com a visita ??s belas salinas de Aveiro.",
                                "https://upload.wikimedia.org/wikipedia/commons/thumb/7/7c/Aveiro-Marais_salants-1967_07_29_29.jpg/1200px-Aveiro-Marais_salants-1967_07_29_29.jpg",
                                "Aveiro",
                                40.644699,
                                -8.662301
                        ));
                lTrips.add(new Trips(3, 20, "Gastronomia",
                                "Visita ??s melhores ofertas gastron??micas de Aveiro",
                                "Visita ??s melhores ofertas gastron??micas de Aveiro, onde puder?? provar do??aria t??pica Aveirense entre muitos outros pratos t??picos da regi??o",
                                "https://media-cdn.tripadvisor.com/media/photo-s/0d/43/90/9b/polvo-a-lagareiro.jpg",
                                "Aveiro",
                                40.635749,
                                -8.649522
                        ));
                lTrips.add(new Trips(4, 20, "Aliados",
                                "Visite uma grande refer??ncia tur??stica do Porto, a Avenida dos Aliados",
                                "Poder?? percorrer a p?? toda a regi??o, passear pelas v??rias ruas extremamente bonitas, visitar v??rios caf??s, a igreja do Carmo, mercado do Bolh??o  e esta????o de s??o Bento, entre outros",
                                "https://turistaprofissional.com/wp-content/uploads/2013/05/downtownportoPraadaLiberdadeeAliados2.jpg",
                                "Porto",
                                41.148148,
                                -8.610847
                        ));
                for(Trips trip : lTrips){
                    dao.insert(trip);
                }
            });
        }
    };
}