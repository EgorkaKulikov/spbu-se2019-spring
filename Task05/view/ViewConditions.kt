package view

import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

internal sealed class ViewCondition

internal class VCHelp(val message: String? = null): ViewCondition() {
    override fun toString() = (if (message == null) "" else (message + "\n\n")) +
            "Основные команды:\n" +
            "    -openArchive <имя архива>\n" +
            "        открывает архив и выводит его содержимое на экран\n" +
            "        пример: -openArchive C:\\MyArchives\\MyZip.zip\n" +
            "    -openFolder <имя папки>\n" +
            "        ищет в открытом архиве указанную папку и выводит её\n" +
            "        содержимое; если папок с таким именем несколько, то\n" +
            "        выводится список найденных папок\n" +
            "        пример: -openFolder abc    (откроет папку/список папок с\n" +
            "                именем abc)\n" +
            "                -openFolder FoldersWithShortName/abc    (откроет\n" +
            "                папку/список папок с именем abc, лежащую(их) в\n" +
            "                папке с именем FoldersWithShortName)\n" +
            "                -openFolder Barnaul/?/School42/*/class11D\n" +
            "                (откроет папку/список папок с именем class11D,\n" +
            "                которая лежит через несколько папок в папке\n" +
            "                School42, которая в свою очередь лежит через одну\n" +
            "                папку в папке Barnaul)\n" +
            "    -find <имя папки/файла>\n" +
            "        ищет в открытом архиве папку/файл и выводит основную\n" +
            "        информацию ней/нём, если папок/файлов с таким именем\n" +
            "        несколько, то выводится список найденных объектов\n" +
            "        пример: -find FoldersWithShortName/?/abc.txt    (найдёт\n" +
            "        файл/папку с именем abc.txt, лежащую через одну папку в\n" +
            "        папке с именем FoldersWithShortName)\n" +
            "    -select <номер объекта>\n" +
            "        выбирает из списка, возвращаемого find, openFolder или\n" +
            "        другими подобными командами, объект с данным номером и\n" +
            "        отображает его в соответствии с предыдущим запросом\n" +
            "        пример: -select 2    (выберет второй объект из списка)\n" +
            "    -moreInfo\n" +
            "        выдаёт более полную информацию об объекте, который\n" +
            "        отображён в момент вызова этой команды\n" +
            "    -exit\n" +
            "        закрывает данное окно\n" +
            "Вы можете сформировать запрос из нескольких команд, разделённых\n" +
            "пробелом; если в вашем запросе в имени папки или файла есть пробел,\n" +
            "то вы можете записать имя папки или файла в двойных кавычках\n" +
            "Пример: -openArchive C:\\\"main archive.zip\" -find Info/*/main.txt\n\n" +
            "Введите запрос:"
}

internal class VCExit: ViewCondition()

internal class VCListFoundObjects(val listObjects: List<Object>, val prevCommand: String, val message: String? = null): ViewCondition() {
    override fun toString(): String {
        var result = if (message == null) "" else (message + "\n\n")
        var number = 1
        for (obj in listObjects) {
            result += "$number.\n" +
                    when (obj) {
                        is File -> "    тип:             файл\n"
                        is Folder -> "    тип:             папка\n"
                        else -> throw Exception("Was founded unexpected class")
                    } +
                    "    имя:             ${obj.name}\n" +
                    "    путь:            ${obj.path.joinToString("/")}/\n" +
                    "    дата изменения:  ${obj.modificationDate.format(DateTimeFormatter
                        .ofPattern("HH:mm:ss dd.MM.yyyy"))}\n" +
                    when (obj) {
                        is File -> "    сжатый размер:   ${obj.compressedSize} байт\n" +
                                "    несжатый размер: ${obj.uncompressedSize} байт\n"
                        is Folder -> "    суммарный сжатый\n    размер файлов:   ${obj.compressedSize} байт\n" +
                                "    суммарный несжатый\n    размер файлов:   ${obj.uncompressedSize} байт\n"
                        else -> throw Exception("Was founded unexpected class")
                    } + "-".repeat(67) + "\n"
            number++
        }
        return result
    }
}

internal class VCFoundObject(val foundObject: Object): ViewCondition() {
    override fun toString() = "Найденный файл:\n\n" +
            when (foundObject) {
                is File -> "    тип:             файл\n"
                is Folder -> "    тип:             папка\n"
                else -> throw Exception("Was founded unexpected class")
            } +
            "    имя:             ${foundObject.name}\n" +
            "    путь:            ${foundObject.path.joinToString("/")}/\n" +
            "    дата изменения:  ${foundObject.modificationDate.format(DateTimeFormatter
                .ofPattern("HH:mm:ss dd.MM.yyyy"))}\n" +
            when (foundObject) {
                is File -> "    сжатый размер:   ${foundObject.compressedSize} байт\n" +
                        "    несжатый размер: ${foundObject.uncompressedSize} байт\n"
                is Folder -> "    суммарный сжатый\n    размер файлов:   ${foundObject.compressedSize} байт\n" +
                        "    суммарный несжатый\n    размер файлов:   ${foundObject.uncompressedSize} байт\n"
                else -> throw Exception("Was founded unexpected class")
            } + "\nЧтобы получить больше информации, наберите -moreInfo\n"
}

internal class VCListFoundObjectsIsEmpty: ViewCondition() {
    override fun toString() = "По вашему запросу ничего не нашлось\n"
}

internal class VCMoreInfoAboutObject(val fileName: List<String?>,
                                     val modificationDate: List<LocalDateTime?>,
                                     val compressedSize: List<Int?>,
                                     val uncompressedSize: List<Int?>,
                                     val compressionMethod: List<Int?>,
                                     val versionToExtract: List<Int?>,
                                     val versionMadeBy: Int,
                                     val extraField: List<String?>,
                                     val fileComment: String,
                                     val fileData: String?): ViewCondition() {
    override fun toString(): String {
        if (fileName.size != 2 || fileName[0] == null)
            throw Exception("There should be 2 entries in the list \"fileName\"")
        if (modificationDate.size != 2 || modificationDate[0] == null)
            throw Exception("There should be 2 entries in the list \"modificationDate\"")
        if (compressedSize.size != 2 || compressedSize[0] == null)
            throw Exception("There should be 2 entries in the list \"compressedSize\"")
        if (uncompressedSize.size != 2 || uncompressedSize[0] == null)
            throw Exception("There should be 2 entries in the list \"uncompressedSize\"")
        if (compressionMethod.size != 2 || compressionMethod[0] == null)
            throw Exception("There should be 2 entries in the list \"compressionMethod\"")
        if (versionToExtract.size != 2 || versionToExtract[0] == null)
            throw Exception("There should be 2 entries in the list \"versionToExtract\"")
        if (extraField.size != 2 || extraField[0] == null)
            throw Exception("There should be 2 entries in the list \"extraField\"")
        val archiveIsCorrect = fileName[0] == fileName[1] &&
                modificationDate[0] == modificationDate[1] &&
                compressedSize[0] == compressedSize[1] &&
                uncompressedSize[0] == uncompressedSize[1] &&
                compressionMethod[0] == compressionMethod[1] &&
                versionToExtract[0] == versionToExtract[1] &&
                extraField[0] == extraField[1]
        var result = if (archiveIsCorrect)
            ""
        else
            "!Внимание, архив (частично) повреждён!\n\n"
        result += "имя:\n    ${fileName[0]}" +
                (if (fileName[0] != fileName[1])
                    "   /   ${fileName[1] ?: "???"}\n"
                else
                    "\n")
        result += "дата изменения:\n    ${modificationDate[0]!!.format(DateTimeFormatter.
                    ofPattern("HH:mm:ss dd.MM.yyyy"))}" +
                (if (modificationDate[0] != modificationDate[1])
                    "   /   ${modificationDate[1]?.format(DateTimeFormatter.
                        ofPattern("HH:mm:ss dd.MM.yyyy")) ?: "???"}\n"
                else
                    "\n")
        result += "сжатый размер:\n    ${compressedSize[0]} байт" +
                (if (compressedSize[0] != compressedSize[1])
                    "   /   ${compressedSize[1] ?: "???"} байт\n"
                else
                    "\n")
        result += "несжатый размер:\n    ${uncompressedSize[0]} байт" +
                (if (uncompressedSize[0] != uncompressedSize[1])
                    "   /   ${uncompressedSize[1] ?: "???"} байт\n"
                else
                    "\n")
        result += "метод сжатия:\n    ${compressionMethod[0]}" +
                (if (compressionMethod[0] != compressionMethod[1])
                    "   /   ${compressionMethod[1] ?: "???"}\n"
                else
                    "\n")
        result += "минимальная версия для распаковки:\n    ${versionToExtract[0].toString()
            .toCharArray().joinToString(".")}" +
                (if (versionToExtract[0] != versionToExtract[1])
                    "   /   ${versionToExtract[1]?.toString()
                        ?.toCharArray()?.joinToString(".") ?: "???"}\n"
                else
                    "\n")
        result += "минимальная версия для создания:\n    ${versionMadeBy.toString()
            .toCharArray().joinToString(".")}\n"
        result += "дополнитальные данные:\n    [${extraField[0]}]" +
                (if (extraField[0] != extraField[1])
                    "   /   [${extraField[1] ?: "???"}]\n"
                else
                    "\n")
        result += "комментарии:\n    [$fileComment]\n"
        result += "содержимое:\n    [$fileData]\n"
        return result
    }
}

internal class VCException(val exception: Exception): ViewCondition() {
    override fun toString() = when (exception) {
        is exceptions.DecryptionException -> "Возможно, данный архив повреждён"
        is exceptions.UserInputException -> "Вы ввели некорректные данные"
        else -> "Что-то пошло не так"
    } + "\n\nОшибка: ${exception.message}"
}

@kotlin.ExperimentalUnsignedTypes
internal class VCArchiveStructure(val archive: StructureObject): ViewCondition() {
    private fun containerToString(container: StructureObject, depthRecursion: Int = 1): String {
        var result = ""
        for (obj in container.listObjects!!) {
            result += "  ".repeat(depthRecursion) + obj.name +
                    if (obj.listObjects == null) "\n"
                    else if (obj.listObjects!!.isEmpty() && depthRecursion < View.maxHeightDisplayedTreeFolder)
                        "\n" + "  ".repeat(depthRecursion) + "  <empty>\n"
                    else if (obj.listObjects!!.isEmpty()) "\n" + "  ".repeat(depthRecursion) + "  ...\n"
                    else ("\n" + containerToString(obj, depthRecursion + 1))
        }
        return result
    }
    override fun toString() = "Структура архива ${archive.name}:\n" + containerToString(archive)
}

@kotlin.ExperimentalUnsignedTypes
internal class VCFolderStructure(val folder: StructureObject): ViewCondition() {
    private fun containerToString(container: StructureObject, depthRecursion: Int = 1): String {
        var result = ""
        for (obj in container.listObjects!!) {
            result += "  ".repeat(depthRecursion) + obj.name +
                    if (obj.listObjects == null) "\n"
                    else if (obj.listObjects!!.isEmpty() && depthRecursion < View.maxHeightDisplayedTreeFolder)
                        "\n" + "  ".repeat(depthRecursion) + "  <empty>\n"
                    else if (obj.listObjects!!.isEmpty()) "\n" + "  ".repeat(depthRecursion) + "  ...\n"
                    else ("\n" + containerToString(obj, depthRecursion + 1))
        }
        return result
    }
    override fun toString() = "Структура папки ${folder.name}:\n" + containerToString(folder)
}
