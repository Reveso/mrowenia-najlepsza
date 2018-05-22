package sample;

public class CustomBehaviorCore {

    public void markAsVisited(Ant ant) {
        if(plane[ant.x][ant.y] == -1)
            plane[ant.x][ant.y] = 0;
    }
    public void antSetDirection(Ant ant) {

        int nextDirection = behaviourArray[plane[ant->x][ant->y]];
        if (nextDirection == RIGHT)
            (ant->kierunek)++;
        else if (nextDirection == LEFT)
            (ant->kierunek) += 3;
        (ant->kierunek) %= 4;
    }
    void antSetPlaneValue(Ant ant) {
        /**
         * Jeśli iteracja wartosci na planszy nie równa się wartości zmiennej behaviourLimit, wtedy iterujemy
         */
        if((plane[ant->x][ant->y] + 1) == behaviourLimit) {
            plane[ant->x][ant->y] = 0;
        } else {
            plane[ant->x][ant->y] = plane[ant->x][ant->y] + 1;
        }
    }
    void antStep(Ant ant, ColorList *colorList, int planeSize, SDL_Renderer *renderer, SDL_Rect r, int antSize) {
        markAsVisited(ant);
        antSetPlaneValue(ant);
        chooseColor(ant, renderer, r, colorList, plane, antSize);

        if(isCrashed(ant, planeSize))
            return;

        antMove(ant);
        markAsVisited(ant);
        antSetDirection(ant);
    }
    void customBehaviourController(int planeSize, int maxSteps, char* behaviourString, int startX, int startY,
                                   ColorList* colorList, bool isInfinity, int refreshRate) {

        /**
         * Tworzenie planszy po ktorej bedzie poruszac sie mrowka
         */
        plane = createPlane(planeSize);
        interpretBehaviourString(behaviourString);

        behaviourLimit = strlen(behaviourString);
        Ant theOnlyAntThatMatters = malloc(sizeof(AntObj));
        /**
         * Ustawienie mrowki na poczatkowej pozycji
         */
        setAntPosition(theOnlyAntThatMatters, startX, startY);
        /**
         * Ustawienie poczatkowego kierunku mrowki
         */
        setAntStartDirection(theOnlyAntThatMatters);


        int antSize=10;

        SDL_Event eva ;
        SDL_Rect r;
        r.w = antSize;
        r.h = antSize;

        SDL_Window* window = NULL;
        window = SDL_CreateWindow
                (
                        "WINCYJ MROWEK", SDL_WINDOWPOS_CENTERED,
                        SDL_WINDOWPOS_CENTERED,
                        antSize*planeSize,
                        antSize*planeSize,
                        SDL_WINDOW_SHOWN
                );

        SDL_Renderer* renderer = SDL_CreateRenderer( window, -1, SDL_RENDERER_ACCELERATED);
        SDL_SetRenderDrawColor( renderer, 255, 255, 255, 255 );
        SDL_RenderClear( renderer );
        SDL_SetRenderDrawColor( renderer, 255, 0, 0, 255 );


        int temp=0;
        while (!isCrashed(theOnlyAntThatMatters, planeSize) && maxSteps--) {
            while(SDL_PollEvent(&eva))
            if(eva.type==SDL_QUIT)
                return;

            if(isInfinity)
                maxSteps++;

            antStep(theOnlyAntThatMatters, colorList, planeSize, renderer, r, antSize);

            if(++temp%refreshRate != 0)
            {
                temp=0;
                continue;
            }


            SDL_UpdateWindowSurface(window);
            SDL_Delay(1);
        }
        system("PAUSE");

        while(SDL_PollEvent(&eva))
        if(eva.type==SDL_QUIT)
            return;

        //printPlane(plane, planeSize);
    }

    void interpretBehaviourString(char* behaviourString) {
        int behaviourStringLength = strlen(behaviourString);
        behaviourArray = malloc(behaviourStringLength*sizeof(int));

        for(int i=0; i<behaviourStringLength; i++) {
            if(behaviourString[i] == 'L')
                behaviourArray[i] = LEFT;
            else if(behaviourString[i] == 'R')
                behaviourArray[i] = RIGHT;
        }
    }

    void setAntStartDirection(Ant ant) {
        if(behaviourArray[0] == LEFT)
            ant->kierunek = 3;
        else if (behaviourArray[0] == RIGHT)
            ant->kierunek = 1;
    }

}
