package sample;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception{
        Parent root = FXMLLoader.load(getClass().getResource("sample.fxml"));
        primaryStage.setTitle("Hello World");
        primaryStage.setScene(new Scene(root, 300, 275));
        primaryStage.show();
    }


    public static void main(String[] args) {
        launch(args);
        /**
         * Lista zawierajaca RGB kolorów używanycvh do tworzenia śladu
         */
        ColorList *colorList = createColorList("colors.cfg");
        /**
         *  Ilość kolorów w colorList
         */
        int colorsCount = countColorsFromList(colorList);
        /**
         * Wielkosc pola na ktorym porusza sie mrowka
         */
        int planeSize;
        /**
         * Maksymalna ilosc krokow mrowki
         */
        int maxSteps;
        /**
         * Ciag liter R i L reprezentujacy zachowanie sie mrowki na planszy
         */
        char* behaviourString = malloc((colorsCount+5)*sizeof(char));
        /**
         * Predkosc animacji
         */
        int sdlFPS;
        /**
         * Zmienna określająca czy jedynym warunkiem konca animacji bedzie kolizja mrowki z krańcem planszy
         */
        bool isInfinity = false;
        /**
         * Ilosc krokow za jednym odswiezeniem animacji
         */
        int refreshRate;

        printf("Podaj dlugosc boku plaszczyzny:\n");
        scanf("%d", &planeSize);

        printf("Podaj maksymalna ilosc krokow mrowki(dla ujemnej bedzie petla nieskonczona):\n");
        scanf("%d", &maxSteps);

        printf("Podaj algorytm zachowania.\nAlgorytm ma byc kombinacja duzych liter L oraz R - LRLRLRRLL.\n"
                "Dla opcji standardowej \"RL\" pojawi sie mozliwosc umieszczenia na plaszczyznie wiecej niz jedna mrowke.\n"
                "Maksymalna dlugosc algorytmu: %d\n", colorsCount);
        scanf("%s", behaviourString);

        if(strlen(behaviourString) > colorsCount) {
            fprintf(stderr, "\nZa dlugi ciag");
            exit(5000);
        }

        if(checkBehaviourString(behaviourString) == false)
            return -1;

        printf("Podaj ile ruchow przy jednym odswiezeniu chcesz widziec:\n");
        scanf("%d", &refreshRate);

        printf("Podaj szybkosc odswiezania(fps):\n");
        scanf("%d", &sdlFPS);

        /**
         * Jesli podana maksymalna ilosc krokow jest mniejsza lub równa 0, animacja skonczy sie tylko po kolizji mrowki z krańcem planszy
         */
        if(maxSteps <= 0) {
            isInfinity = true;
            maxSteps = 10;
        }

        if(strcmp(behaviourString, "RL") == 0) {
            /**
             * Ilość mrówek symulowanych na planszy
             */
            int antCount;
            printf("Podaj ilosc mrowek ktore chcesz umiescic na plaszczyznie:\n"
                    "Maksymalna ilosc mrowek: %d\n", colorsCount);
            scanf("%d", &antCount);

            if(antCount > colorsCount) {
                fprintf(stderr, "\nZa duza ilosc mrowek");
                exit(4000);
            }

            /**
             * Przesłanie ustawionej konfiguracji do basicAntController()
             */
            new basicAntController(planeSize, maxSteps, antCount, animationSpeed, colorList, isInfinity, refreshRate);
        } else {
            /**
             * Początkowa pozycja mrówki symulowanej na planszy
             */
            int startX, startY;
//            printf("Podaj wspolrzedne poczatkowe mrowki:\n");
            System.out.println("Podaj wspolrzedne poczatkowe mrowki:");
//            scanf("%d%d", &startX, &startY);

            new customBehaviourController(planeSize, maxSteps, behaviourString, startX, startY, colorList, isInfinity, refreshRate);
        }
        printf("--\n");
        return 0;
    }
    }
}
