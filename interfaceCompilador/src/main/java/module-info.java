module interfacecompilador.interfacecompilador {
    requires javafx.controls;
    requires javafx.fxml;


    opens interfacecompilador.interfacecompilador to javafx.fxml;
    exports interfacecompilador.interfacecompilador;
}