import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.data.general.DefaultPieDataset;
import org.jfree.data.general.PieDataset;
import org.jfree.ui.ApplicationFrame;
import org.jfree.ui.RefineryUtilities;

import javax.swing.JPanel;
import java.io.BufferedReader;
import java.io.InputStreamReader;

public class Chart extends ApplicationFrame {

    SimpleClassification simpleClassification;
    WekaClassification wekaClassification;
    MoaClassification moaClassification;

    Chart(
            SimpleClassification simpleClassification,
            WekaClassification wekaClassification,
            MoaClassification moaClassification
    ) {
        super("test");
        this.simpleClassification = simpleClassification;
        this.wekaClassification = wekaClassification;
        this.moaClassification = moaClassification;
        setContentPane(createDemoPanel( ));
    }

    public void menu() {
        String menuNumber = "";

        System.out.println("[1] To see chart");
        System.out.println("[3] Main menu");

        BufferedReader reader =
                new BufferedReader(new InputStreamReader(System.in));

        try {
            menuNumber = reader.readLine();
        } catch (Exception e) {
            System.out.println(ConsoleColors.ansiRedMessage(e));
            menu();
        }

        switch (menuNumber) {
            case "1":
                run();
                Main.menu();
                break;
            case "3":
                Main.menu();
                break;
            default:
                System.out.println(ConsoleColors.ANSI_RED_BACKGROUND + "Invalid input" + ConsoleColors.ANSI_RESET);
                menu();
        }
    }

    public void run() {
        this.setSize( 560 , 367 );
        RefineryUtilities.centerFrameOnScreen( this );
        this.setVisible( true );
    }

    private static PieDataset createDataset( ) {
        DefaultPieDataset dataset = new DefaultPieDataset( );
        dataset.setValue( "IPhone 5s" , new Double( 20 ) );
        dataset.setValue( "SamSung Grand" , new Double( 20 ) );
        dataset.setValue( "MotoG" , new Double( 40 ) );
        dataset.setValue( "Nokia Lumia" , new Double( 10 ) );
        return dataset;
    }

    private static JFreeChart createChart(PieDataset dataset ) {
        JFreeChart chart = ChartFactory.createPieChart(
                "Mobile Sales",   // chart title
                dataset,          // data
                true,             // include legend
                true,
                false);

        return chart;
    }

    public static JPanel createDemoPanel( ) {
        JFreeChart chart = createChart(createDataset( ) );
        return new ChartPanel( chart );
    }
}
