import java.io.*;
import java.util.*;

public class Copy {

    public static long converbytes(long fileSize) {

        long converted = fileSize / 1024;
        if (converted != 0 && fileSize % 1024 != 0)
            converted++;

        return converted;
    
    }
    
    public static void printSize(File file, String format) {

        long bytes = file.length();
        long kilobytes = converbytes(bytes);
        long megabytes = converbytes(kilobytes);
        long gigabytes = converbytes(megabytes);
        long terabytes = converbytes(gigabytes);
        long petabytes = converbytes(terabytes);
        long exabytes = converbytes(petabytes);
        long zettabytes = converbytes(exabytes);
        long yottabytes = converbytes(zettabytes);
        
        if (yottabytes != 0)
            System.out.printf(format + ">Total %dYB were copied!\n", yottabytes);
        else if (zettabytes != 0)
            System.out.printf(format + ">Total %dZB were copied!\n", zettabytes);
        else if (exabytes != 0)
            System.out.printf(format + ">Total %dEB were copied!\n", exabytes);
        else if (petabytes != 0)
            System.out.printf(format + ">Total %dPB were copied!\n", petabytes);
        else if (terabytes != 0)
            System.out.printf(format + ">Total %dTB were copied!\n", terabytes);
        else if (gigabytes != 0)
            System.out.printf(format + ">Total %dGB were copied!\n", gigabytes);
        else if (megabytes != 0)
            System.out.printf(format + ">Total %dMB were copied!\n", megabytes);
        else if (kilobytes != 0)
            System.out.printf(format + ">Total %dKB were copied!\n", kilobytes);
        else
            System.out.printf(format + ">Total %dB were copied!\n", bytes);

    } 

    public static void recursionCopy(File source, String strdest, String format) {
        
        if (!source.exists()) {
            System.out.println(format + ">The file or directory by the path " + source.getPath() + " does not exists, please, give the valid path!");
            return;
        }

        if (source.isFile()) {

            File dest = new File(strdest);

            if (dest.isFile()) {
                System.out.println(format + ">File by the path " + dest.getPath() + " exists!");
                return;
            }

            dest.getParentFile().mkdirs();

            try (FileInputStream fis = new FileInputStream(source); FileOutputStream fos = new FileOutputStream(dest)) {

                System.out.println(format + ">Started: " + source.getPath() + "...");

                int mb = 1048576;
                byte b[] = new byte[mb];
                int res;
                while ((res = fis.read(b)) != -1) {
                    fos.write(b, 0, res);
                }

                System.out.println(format + ">Finished FILE: " + source.getPath());
                printSize(source, format);

            }
            catch (IOException ex) {
                System.out.println(format + ">Something went wrong during the reading and writing of file!");
            }
            catch (Exception ex) {
                System.out.println(format + ">" + ex.getMessage());
            }
            finally {
                return;
            }

        }

        System.out.println(format + ">Started: " + source.getPath() + "...");

        for (File sub : source.listFiles()) {

            String folder = strdest + "\\" + sub.getName();
            recursionCopy(sub, folder, format + "    ");

        }

        System.out.println(format + ">Finished FOLDER: " + source.getPath());
        System.out.println();

    }

    public static void copyDirectory_File(String strsource, String strdest) {

        File source = new File(strsource);
        File destDir = new File(strdest);

        if (!destDir.exists()) {
            destDir.mkdirs();
        }
        
        strdest = strdest + "\\" + source.getName();
        recursionCopy(source, strdest, "    ");

    }

    public static void main(String[] args) {

        if (args.length < 2) {
            System.out.println("Please, enter 2 or more names: the first ones are for the files or directories and the last one is for the destination!");
        }
        else { 
            for (int i = 0; i < args.length - 1; i++) {
                copyDirectory_File(args[i], args[args.length - 1]);
            }   
        }
       
    }

}
