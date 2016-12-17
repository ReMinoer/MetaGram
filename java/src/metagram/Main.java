package metagram;

import metagram.antlr.*;
import org.antlr.v4.runtime.*;
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args)
    {
        if (args.length == 0)
        {
            PrintUsage();
            return;
        }

        File grammarFile = null;
        File outputDirectory = null;
        boolean defaultTarget = false;
        List<String> targets = new ArrayList<String>();

        for (int i = 0; i < args.length; i++)
        {
            String arg = args[i];

            if (arg.startsWith("--"))
            {
                switch (arg)
                {
                    case "--default":
                        defaultTarget = true;
                        continue;
                }
            }
            else if (grammarFile == null)
                grammarFile = new File(arg);
            else if (outputDirectory == null)
                outputDirectory = new File(arg);
            else
                targets.add(args[i]);
        }

        if (grammarFile == null)
        {
            System.out.println("ERROR: <grammarFile> not provided !");
            PrintUsage();
            return;
        }

        if (outputDirectory == null)
        {
            System.out.println("ERROR: <outputDirectory> not provided !");
            PrintUsage();
            return;
        }

        ANTLRFileStream in;
        try
        {
            in = new ANTLRFileStream(grammarFile.getAbsolutePath());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return;
        }

        MetaGramLexer lexer = new MetaGramLexer(in);
        CommonTokenStream tokens = new CommonTokenStream(lexer);
        MetaGramParser parser = new MetaGramParser(tokens);
        MetaGramParser.ParseContext context = parser.parse();

        TargetVisitor targetVisitor;
        if (defaultTarget)
        {
            String result = new TargetVisitor().visit(context);

            File targetDirectory = new File(outputDirectory, "default/");
            targetDirectory.mkdirs();
            File targetFile = new File(targetDirectory, grammarFile.getName());

            try
            {
                PrintWriter out = new PrintWriter(targetFile);
                out.print(result);
                out.close();
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
                return;
            }
        }

        for (int i = 0; i < targets.size(); i++)
        {
            String target = targets.get(i);
            String result = new TargetVisitor(target).visit(context);

            File targetDirectory = new File(outputDirectory, target + "/");
            targetDirectory.mkdirs();
            File targetFile = new File(targetDirectory, grammarFile.getName());

            try
            {
                PrintWriter out = new PrintWriter(targetFile);
                out.print(result);
                out.close();
            }
            catch (FileNotFoundException e)
            {
                e.printStackTrace();
                return;
            }
        }
    }

    private static void PrintUsage()
    {
        System.out.println("Usage: MetaGram <grammarFile> <outputDirectory> (--default)? (<targetName>)*");
    }
}
