import java.util.Scanner;
import java.io.File;
import java.io.FileWriter;

class Main
{
    public static void main(String[] args)
    {
        //input scanner
        Scanner input = new Scanner(System.in);
        //bit and gem rates
        double bitRate;
        double gemRate;
        //ticket and gem price for all bits
        int ticketPrice;
        int gemPrice;
        //gemrate converted to string to print
        String gemRateString;
        //array holding every line of bits
        String[] line = new String[3];

        //get bit and gem rates
        System.out.print("bits per ticket?: ");
        bitRate = input.nextDouble();
        
        System.out.print("gems per ticket?: ");
        gemRate = input.nextDouble();

        System.out.println("Got it, parsing");

        //if gem rate is perfect (no decimal), then remove the .0 when converting to string
        if (gemRate*10%10 == 0)
        {
            gemRateString = String.format("%.0f", gemRate);
        }
        else
        {
            gemRateString = Double.toString(gemRate);
        }

        input.close();

        //actually write stuff
        try
        {
            //create files
            File inputFile = new File("bits.txt");
            Scanner fileReader = new Scanner(inputFile);

            File outputFile = new File("output.txt");
            FileWriter fileWriter = new FileWriter(outputFile);

            //write the header
            fileWriter.write("__**Selling Bits**__\n");
            //write bit/gem rates
            fileWriter.write(String.format("%.0f", bitRate) + " bits : 1 :tickets: : " + gemRateString + " :gem:\n\n");

            //write every line of bits with the bold stuff until you get to the total bits line
            while (fileReader.hasNextLine())
            {
                line = fileReader.nextLine().split(" ");

                if (line[1].equalsIgnoreCase("total"))
                {
                    break;
                }

                fileWriter.write("**" + line[2] + "** bits: **" + line[0] + "**\n");
            }

            //get ticket and gem price for everything and remove the decimal
            ticketPrice = (int) Math.floor(Double.parseDouble(line[0].replace(",", ""))/bitRate);

            gemPrice = (int) Math.floor(ticketPrice * gemRate);

            //write total gem and ticket price
            fileWriter.write("\nTotal: " + line[0] + " bits (" + ticketPrice + " :tickets: / " + gemPrice + " :gem: for all)\n\n");

            fileWriter.write("#🏧┃fast-order Any amount is appreciated, thank you!");

            fileReader.close();
            fileWriter.close();
        }
        catch (Exception e)
        {
            System.out.println("something screwed up");
            e.printStackTrace();
        }
    }
}