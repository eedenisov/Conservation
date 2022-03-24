import java.io.*;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;


public class Main {

    public static void main(String[] args) throws IOException {
        GameProgress[] gp = {
                new GameProgress("Beginner", 5, 3, 1, 0),
                new GameProgress("Middle", 99, 70, 31, 100),
                new GameProgress("Pro", 1_000, 999, 100, 1_000)
        };
        String[] fileList = {
                new String("/Users/denisovbl/Documents/Евгений/it/Java/Installation/Games/savegames/save_1.dat"),
                new String("/Users/denisovbl/Documents/Евгений/it/Java/Installation/Games/savegames/save_2.dat"),
                new String("/Users/denisovbl/Documents/Евгений/it/Java/Installation/Games/savegames/save_3.dat")
        };

        String zip = "/Users/denisovbl/Documents/Евгений/it/Java/Installation/Games/savegames/zip.zip";
        String way = "/Users/denisovbl/Documents/Евгений/it/Java/Installation/Games/savegames/";
        String saveGame = "/Users/denisovbl/Documents/Евгений/it/Java/Installation/Games/savegames/save_3.dat";

        createFile(fileList);

        saveGame(fileList[0], gp[0]);
        saveGame(fileList[1], gp[1]);
        saveGame(fileList[2], gp[2]);

        zipFiles(zip, fileList);

        deleteFiles(fileList);

        openZip(zip, way);

        GameProgress save = openProgress(saveGame);
        System.out.println(save);
    }

    public static void createFile(String[] file) {
        try {
            for (String f : file) {
                File newfile = new File(f);
                if (newfile.createNewFile()) {
                    System.out.println(newfile.getName() + " - файл успешно создан");
                } else
                    System.out.println(newfile.getName() + " - ошибка при создании файла");
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void saveGame(String fileList, GameProgress gp) {
        try (FileOutputStream fos = new FileOutputStream(fileList);
             ObjectOutputStream oos = new ObjectOutputStream(fos)) {
            oos.writeObject(gp);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void zipFiles(String zip, String[] fileList) throws IOException {
        try (FileOutputStream fos = new FileOutputStream(zip);
             ZipOutputStream zout = new ZipOutputStream(fos)) {
            for (String s : fileList) {
                File fileToZip = new File(s);
                try (FileInputStream fis = new FileInputStream(fileToZip)) {
                    ZipEntry entry = new ZipEntry(fileToZip.getName());
                    zout.putNextEntry(entry);
                    byte[] bytes = new byte[fis.available()];
                    fis.read(bytes);
                    zout.write(bytes);
                    zout.closeEntry();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }
    }

    public static void deleteFiles(String[] fileList) {
        for (String delete : fileList) {
            File del = new File(delete);
            if (del.delete()) {
                System.out.println(del.getName() + " - файл удалён");
            } else {
                System.out.println(del.getName() + " - ошибка при удалении файла");
            }
        }
    }

    public static void openZip(String zip, String way) {
        try (ZipInputStream zis = new ZipInputStream(
                new FileInputStream(zip))) {
            ZipEntry entry;
            String name;
            while ((entry = zis.getNextEntry()) != null) {
                name = entry.getName();
                FileOutputStream fout = new FileOutputStream(way + name);
                for (int c = zis.read(); c != -1; c = zis.read()) {
                    fout.write(c);
                }
                fout.flush();
                zis.closeEntry();
                fout.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static GameProgress openProgress(String saveGame) {
        GameProgress gp = null;
        try (FileInputStream fis = new FileInputStream(saveGame);
             ObjectInputStream ois = new ObjectInputStream(fis)) {
            gp = (GameProgress) ois.readObject();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return gp;
    }
}

