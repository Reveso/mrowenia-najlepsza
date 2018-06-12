package application.langtonsant.entity;

import application.langtonsant.CoreType;

import java.util.List;
import java.util.Map;

/**
 * Klasa zawierająca konfigurację do stworzenia nowej animacji mrówki langtona.
 */
public class ConfigurationSetup {
    /**
     * Definiuje czy konfiguracja instancji jest kompletna.
     */
    private boolean isComplete = false;

    /**
     * Definiuje rozmiar kwadratu mrówki na płótnie.
     */
    private int antSize;
    /**
     * Definiuje opóźnienie przy kroku mrówki.
     */
    private int refreshDelay;
    /**
     * Definiuje limit kroków animacji.
     */
    private Long stepsLimit;

    /**
     * Definiuje obecną ilość kroków mrówki.
     * Nierówna 0 dla wczytanych pozycji.
     */
    private Long currentSteps;
    /**
     * Definiuje listę mrówek poruszających się po płótnie.
     */
    private List<Ant> antList;
    /**
     * Definiuje mapę kolorów do użycia na płótnie.
     */
    private Map<Integer, SavableColor> colorMap;
    /**
     * Definiuje typ jądra animacji.
     */
    private CoreType coreType;
    /**
     * Definiuje płaszczyznę po której porusza się mrówka.
     */
    private Plane plane;

    /**
     * Zwraca wartość właściwości isComplete.
     * @return Właściwiość isComplete.
     */
    public boolean isComplete() {
        return isComplete;
    }

    /**
     * Ustawia wartość isComplete.
     * @param complete Pożądana wartość isComplete.
     */
    public void setComplete(boolean complete) {
        isComplete = complete;
    }

    /**
     * Zwraca wartość właściwości refreshDelay.
     * @return Właściość refreshDelay.
     */
    public int getRefreshDelay() {
        return refreshDelay;
    }

    /**
     * Ustawia właściwość refreshDelay.
     * @param refreshDelay Pożądana wartość refreshDelay.
     */
    public void setRefreshDelay(int refreshDelay) {
        this.refreshDelay = refreshDelay;
    }

    /**
     * Zwraca właściwość stepsLimit.
     * @return Właściwość stepsLimit.
     */
    public Long getStepsLimit() {
        return stepsLimit;
    }

    /**
     * Ustawia wartość właściwości stepsLimit.
     * @param stepsLimit Pożądana wartość dla stepsLimit.
     */
    public void setStepsLimit(Long stepsLimit) {
        this.stepsLimit = stepsLimit;
    }

    /**
     * Zwraca właściwość currentSteps.
     * @return Właściwość currentSteps.
     */
    public Long getCurrentSteps() {
        return currentSteps;
    }

    /**
     * Ustawia wartość właściwości currentSteps.
     * @param currentSteps Pożądana wartość dla currentSteps.
     */
    public void setCurrentSteps(Long currentSteps) {
        this.currentSteps = currentSteps;
    }

    /**
     * Zwraca właściwość antList.
     * @return lista antList.
     */
    public List<Ant> getAntList() {
        return antList;
    }

    /**
     * Ustawia właściwość antList.
     * @param antList Kompletna lista mrówek.
     */
    public void setAntList(List<Ant> antList) {
        this.antList = antList;
    }

    /**
     * Zwraca mapę kolorów colorMap.
     * @return Mapa kolorów.
     */
    public Map<Integer, SavableColor> getColorMap() {
        return colorMap;
    }

    /**
     * Ustawia mapę kolorów colorMap.
     * @param colorMap Kompletna mapa kolorów.
     */
    public void setColorMap(Map<Integer, SavableColor> colorMap) {
        this.colorMap = colorMap;
    }

    /**
     * Zwraca właściwość coreType.
     * @return Właściwość coreType.
     */
    public CoreType getCoreType() {
        return coreType;
    }

    /**
     * Ustawia wartość dla właściwości coreType.
     * @param coreType Pożądana wartość dla coreType.
     */
    public void setCoreType(CoreType coreType) {
        this.coreType = coreType;
    }

    /**
     * Zwraca właściwość plane.
     * @return Właściwość plane.
     */
    public Plane getPlane() {
        return plane;
    }

    /**
     * Ustawia właściwość plane.
     * @param plane Instancja klasy application.langtonsant.entity.Plane.
     */
    public void setPlane(Plane plane) {
        this.plane = plane;
    }

    /**
     * Zwraca wartość antSize.
     * @return Wartość właściwości antSize.
     */
    public int getAntSize() {
        return antSize;
    }

    /**
     * Ustawia wartość antSize.
     * @param antSize Pożądana wartośc dla antSize.
     */
    public void setAntSize(int antSize) {
        this.antSize = antSize;
    }
}
