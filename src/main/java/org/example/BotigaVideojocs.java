package org.example;

import java.util.Objects;
import java.util.Scanner;

public class BotigaVideojocs {

    public static int CONTADOR=1;
    public static int CONTADOR2=0;


    public static void main(String[] args) {

        visualizarMenu();
    }
    public static void visualizarMenu(){

        Scanner teclado=new Scanner(System.in);
        boolean hacerBucle;
        String [][] catalogoVideojuegos=catalogoDeVideojuegos();
        String [][] catalogoVideojuegosStock=arrayPunto2Stock(catalogoVideojuegos);


        do {
            System.out.println("");
            System.out.println("Bienvenido a la tienda de videojuegos. Elija una opción");
            System.out.println(
                    "\t1. Consultar catálogo de videojuegos\n" +
                    "\t2. Consultar stock de videojuegos\n" +
                    "\t3. Mostrar catálogo ordenado por stock\n" +
                    "\t4. Mostrar catálogo ordenado por precio\n" +
                    "\t5. Registrar venta de videojuegos\n" +
                    "\t6. Registrar devolución de videojuegos\n" +
                    "\t7. Salir de la aplicación");

            int seleccionUser=0;
            hacerBucle=true;

            if (teclado.hasNextInt()){
                seleccionUser= teclado.nextInt();
                if (seleccionUser<0 || seleccionUser >7){
                    hacerBucle=true;
                }
            }else {
                hacerBucle=true;
                teclado.next();
            }

            switch (seleccionUser){

                case 1:
                    visualizarpunto1(catalogoVideojuegos);
                    break;
                case 2:
                    visualizarStock(catalogoVideojuegosStock);
                    break;
                case 3:
                    ordenarStock(catalogoVideojuegosStock);
                    break;
                case 4:
                    ordenarPorPrecio(catalogoVideojuegos);
                    break;
                case 5:
                    String [][] gestionVentas=ArrayRegistros(crearArrayMezcla(catalogoVideojuegos,catalogoVideojuegosStock));
                    break;
                case 6:
                    retornarVenta(ArrayRegistros(crearArrayMezcla(catalogoVideojuegos,catalogoVideojuegosStock)),catalogoVideojuegosStock);
                    break;
                case 7:
                    hacerBucle=false;
                    break;
            }

        }while (hacerBucle);

    }
    public static String [][] crearArrayMezcla(String [][] catalogoVideojuegos,String [][] catalogoStaock){

        String [][] catalogoVideojuegosYStock=new String[catalogoVideojuegos.length][catalogoVideojuegos.length+1];

        for (int i = 0; i < 10; i++) {
                catalogoVideojuegosYStock[i][0]=catalogoVideojuegos[i][0];
                catalogoVideojuegosYStock[i][1]=catalogoVideojuegos[i][1];
                catalogoVideojuegosYStock[i][2]=catalogoVideojuegos[i][2];
                catalogoVideojuegosYStock[i][3]=catalogoStaock[i][2];
        }
        return catalogoVideojuegosYStock;
    }

    public static String[][] ArrayRegistros(String [][] StockMásPrecio){

        boolean datosCorrectos = true;
        int introducirDato;
        int introducirDato2;
        String siOno;
        int sumar=0;
        int contadorNumProductos = 0;
        Scanner teclado = new Scanner(System.in);

        String[][] arrayRegistroVentas = new String[50][3];

        do {
            System.out.println("Introduzca el código del videojuego que desea comprar (entre 1 y 10):");
            introducirDato = teclado.nextInt();

            if (introducirDato < 1 || introducirDato > 10) {
                System.out.println("Código inválido. Debe estar entre 1 y 10.");
                datosCorrectos = false;
            } else {
                System.out.println("Introduzca la cantidad de unidades que desea comprar:");
                introducirDato2 = teclado.nextInt();

                if (introducirDato2 > Integer.parseInt(StockMásPrecio[introducirDato - 1][3])) {
                    System.out.println("Lo siento, no tenemos suficiente stock para este videojuego.");
                    datosCorrectos = false;
                } else {
                    arrayRegistroVentas[contadorNumProductos][0] = String.valueOf(CONTADOR);
                    arrayRegistroVentas[contadorNumProductos][1] = String.valueOf(introducirDato);
                    arrayRegistroVentas[contadorNumProductos][2] = String.valueOf(introducirDato2);

                    contadorNumProductos++;
                    CONTADOR2++;

                    System.out.println("¿Desea comprar algún videojuego más? (S/N): ");
                    siOno = teclado.next();
                    if (siOno.equals("N")) {
                        CONTADOR++;
                        datosCorrectos=true;
                        ResumenDeLaVenta(arrayRegistroVentas,StockMásPrecio);
                    }else {
                        datosCorrectos=false;
                    }
                }
            }
        } while (!datosCorrectos);

        return arrayRegistroVentas;
    }

    public static void ResumenDeLaVenta(String[][] arrayRegistroVentas, String[][] stockMásPrecio) {
        double totalPagar = 0.0;

        System.out.println("Resumen de la venta (código 1)\n");
        System.out.printf("+--------+--------------------+----------+\n");
        System.out.printf("| %-6s | %-18s | %-8s |\n", "Código", "Nombre", "Unidades");
        System.out.printf("+--------+--------------------+----------+\n");

        for (int i = 0; i < arrayRegistroVentas.length && arrayRegistroVentas[i][0] != null; i++) {
            int codigoJuego = Integer.parseInt(arrayRegistroVentas[i][1]);
            String nombreJuego = stockMásPrecio[codigoJuego - 1][1];
            int unidadesVendidas = Integer.parseInt(arrayRegistroVentas[i][2]);
            double precioUnitario = Double.parseDouble(stockMásPrecio[codigoJuego - 1][2]);
            double subtotal = unidadesVendidas * precioUnitario;
            totalPagar += subtotal;

            System.out.printf("| %-6s | %-18s | %-8s |\n", codigoJuego, nombreJuego, unidadesVendidas);
        }

        System.out.printf("+--------+--------------------+----------+\n");
        System.out.printf("Total a pagar: %.2f €\n", totalPagar);
        System.out.println("\nGracias por su compra.");
    }




    public static void retornarVenta(String [][] Codigoventa,String[][] StockPrecio){
        boolean encomtradp=false;
        boolean preguntarDeNuevo=false;
        int []codigoJuegos=new int[Codigoventa.length];
        int [] precios=new int[Codigoventa.length];
        int [] unidaes=new int[Codigoventa.length];
        int indexJuegos=0;
        int indicePrecios=0;
        do {
            Scanner teclado=new Scanner(System.in);
            System.out.println("Introduzca el código de venta");
            int codigo= teclado.nextInt();


            for (int i = 0; i <Codigoventa.length ; i++) {
                if (codigo == Integer.parseInt(Codigoventa[i][0])) {
                    encomtradp = true;
                    codigoJuegos[indexJuegos]=Integer.parseInt(Codigoventa[i][1]);
                    codigoJuegos[indexJuegos]=Integer.parseInt(Codigoventa[i][2]);
                    indexJuegos++;
                }
            }

            for (int juego:codigoJuegos) {
                for (int i = 0; i <StockPrecio.length ; i++) {
                    if (juego == Integer.parseInt(Codigoventa[i][0])){
                        precios[indicePrecios]= Integer.parseInt(StockPrecio[i][2]);
                        indicePrecios++;

                    }
                }
            }
            int o=0;

            for (int i = 0; i <StockPrecio.length ; i++) {
                o=precios[i]*unidaes[i];
            }

            System.out.println("Su devolución ha sido realizada. Se devuelve un importe de "+o);

                if(!encomtradp){
                    System.out.println("Quieres volver a buscarlo");
                    String respuesta= teclado.next();
                    if (respuesta.equals("si") || respuesta.equalsIgnoreCase("s")){
                        preguntarDeNuevo=true;
                    }else {
                        preguntarDeNuevo= false;
                    }

                    //TODO esto solo se preguntara cuando haya fallado la busqueda de la venta, si no, deberia de salir del tiron
                }
        }while (preguntarDeNuevo);



    }
    public static String [][] arrayPunto2Stock(String [][]catalogo1){

        String array [][] = new String[10][3];

        for (int i = 0; i < 10; i++) {
            for (int j = 0; j < 3; j++) {
                array[i][j]=catalogo1[i][j];
                array[i][2]= String.valueOf(Random());
            }
        }
        return array;
    }


    public static void ordenarStock(String[][] arrayStock) {
        System.out.println("Catálogo de videojuegos:");
        System.out.println("+---------------------+---------------------+---------------------+");
        System.out.println("|        Código       |        Nombre       |            Stock    |");
        System.out.println("+---------------------+---------------------+---------------------+");

        int[] arrayPosiciones = retronarAscendentePosicion(arrayStock);

        for (int i = 0; i < arrayStock.length; i++) {
            int pos = arrayPosiciones[i];
            for (int j = 0; j < arrayStock[i].length; j++) {
                System.out.printf("|%"+21+"s", arrayStock[pos][j]);
            }
            System.out.print("|");
            System.out.println("");
        }
        System.out.println("+---------------------+---------------------+---------------------+");
    }
    public static void ordenarPorPrecio(String[][] arrayPrecios) {

        System.out.println("Catálogo de videojuegos:");
        System.out.println("+---------------------+---------------------+---------------------+");
        System.out.println("|        Código       |        Nombre       |            Precio   |");
        System.out.println("+---------------------+---------------------+---------------------+");

        int[] arrayPosiciones = retronarDescendentePosicion(arrayPrecios);

        for (int i = 0; i < arrayPrecios.length; i++) {
            int pos = arrayPosiciones[i];
            for (int j = 0; j < arrayPrecios[i].length; j++) {
                System.out.printf("|%"+21+"s", arrayPrecios[pos][j]);
            }
            System.out.print("|");
            System.out.println("");
        }
        System.out.println("+---------------------+---------------------+---------------------+");
    }

    public static int[] retronarAscendentePosicion(String[][] arrayStock) {

        int[] posicion = new int[arrayStock.length];

        for (int i = 0; i < arrayStock.length; i++) {
            posicion[i] = i;
        }

        for (int i = 0; i < arrayStock.length - 1; i++) {
            for (int j = i + 1; j < arrayStock.length; j++) {

                int stockI = Integer.parseInt(arrayStock[posicion[i]][2]);
                int stockJ = Integer.parseInt(arrayStock[posicion[j]][2]);

                if (stockI > stockJ) {
                    // Intercambiar posiciones
                    int temp = posicion[i];
                    posicion[i] = posicion[j];
                    posicion[j] = temp;
                }
            }
        }
        return posicion;
    }

    public static int[] retronarDescendentePosicion(String[][] arrayStock) {
        int[] posicion = new int[arrayStock.length];

        for (int i = 0; i < arrayStock.length; i++) {
            posicion[i] = i;
        }

        for (int i = 0; i < arrayStock.length - 1; i++) {
            for (int j = i + 1; j < arrayStock.length; j++) {
                double stockI = Double.parseDouble(arrayStock[posicion[i]][2]);
                double stockJ = Double.parseDouble(arrayStock[posicion[j]][2]);

                if (stockI < stockJ) {
                    // Intercambiar posiciones
                    int temp = posicion[i];
                    posicion[i] = posicion[j];
                    posicion[j] = temp;
                }
            }
        }
        return posicion;
    }


    public static void visualizarpunto1(String [][] catalogo1){
        System.out.println("Catálogo de videojuegos:");
        System.out.println("+---------------------+---------------------+---------------------+");
        System.out.println("|        Código       |        Nombre       |            Precio   |");
        System.out.println("+---------------------+---------------------+---------------------+");

        for (int i = 0; i < 10 ; i++) {
            for (int j = 0; j < 3 ; j++) {
                System.out.printf("|%"+21+"s",catalogo1[i][j]);
            }
            System.out.print("|");
            System.out.println("");
        }
        System.out.println("+---------------------+---------------------+---------------------+");
    }

    public static void visualizarStock(String [][] catalogo1){

        System.out.println("Catálogo de videojuegos:");
        System.out.println("+---------------------+---------------------+---------------------+");
        System.out.println("|        Código       |        Nombre       |            Stock    |");
        System.out.println("+---------------------+---------------------+---------------------+");


        for (int i = 0; i < 10 ; i++) {
            for (int j = 0; j < 3 ; j++) {
                System.out.printf("|%"+21+"s",catalogo1[i][j]);
            }
            System.out.print("|");
            System.out.println("");
        }
        System.out.println("+---------------------+---------------------+---------------------+");

    }

    public static String [][] catalogoDeVideojuegos(){

        String [][] catalogoVideojuegos={{"1","Super Mario Bros","19.99"},{"2","The Legend of Zelda","24.99"},{"3","Sonic the Hedgehog","14.99"},{"4","Tetris","9.99"},
                {"5","Pac-Man","4.99"},{"6","Street Fighter 2","29.99"},{"7","Doom","39.99"},{"8","Minecraft","19.99"},{"9","The Sims","34.99"},{"10","Grand Theft Auto V","49.99"}};

        return catalogoVideojuegos;
    }

    public static int Random(){
        return (int)(Math.random() *20);
    }
}