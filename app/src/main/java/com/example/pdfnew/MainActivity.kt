package com.example.pdfnew

import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.os.Bundle
import android.os.Environment
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import com.itextpdf.io.image.ImageDataFactory
import com.itextpdf.kernel.pdf.PdfName.BaseFont
import com.itextpdf.kernel.pdf.PdfWriter
import com.itextpdf.layout.Document
import com.itextpdf.layout.element.Cell
import com.itextpdf.layout.element.Image
import com.itextpdf.layout.element.Paragraph
import com.itextpdf.layout.element.Table
import com.itextpdf.layout.property.TextAlignment
import java.io.ByteArrayOutputStream
import java.io.File
import java.io.FileOutputStream


class MainActivity : AppCompatActivity() {
    private lateinit var btn:Button
    private lateinit var pdf: PdfWriter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.example.pdfnew.R.layout.activity_main)
        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.WRITE_EXTERNAL_STORAGE
            ) != PackageManager.PERMISSION_GRANTED
        ) {
            ActivityCompat.requestPermissions(
                this,
                arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
                101
            )
        }

        ActivityCompat.requestPermissions(
            this,
            arrayOf(android.Manifest.permission.WRITE_EXTERNAL_STORAGE),
            101
        )
        btn= findViewById(com.example.pdfnew.R.id.button)
        btn.setOnClickListener{
             createPdf()
        }

    }

    private fun createPdf() {
        val title = "new.pdf"
        val path = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOCUMENTS)
        val file = File(path, title)
        if (!file.exists()) file.createNewFile()
        pdf = PdfWriter(FileOutputStream(file))
        val pdfDoc = com.itextpdf.kernel.pdf.PdfDocument(pdf)
        var string = "Rent Ready CheckList"
        val para = Paragraph(string).setBold().setTextAlignment(TextAlignment.CENTER).setFontSize(30f)
        pdfDoc.addNewPage()
        val doc =  Document(pdfDoc)
        doc.setMargins(24f, 24f, 32f, 32f)


        val bm = BitmapFactory.decodeResource(resources,com.example.pdfnew.R.drawable.home)
        val stream = ByteArrayOutputStream()
        bm.compress(Bitmap.CompressFormat.PNG, 100, stream)
        var img: Image? = null
        val byteArray: ByteArray = stream.toByteArray()
        val imgdata = ImageDataFactory.create(byteArray)
        img = Image(imgdata)
        doc.add(para)
        doc.add(img.setWidth(50f).setHeight(50f).setMarginLeft(400f).setMarginTop(10f))
        val tableHeader = arrayOf("Item", "Item", "Item")
        val str = arrayOf(arrayOf("Item1", "Mr1","Mr3"), arrayOf("Item1", "Mr2","Mr3"), arrayOf("item","you","Mr4"),arrayOf("item","you","Mr5"),arrayOf("item","you","Mr6"))
        val columnWidth = floatArrayOf(200f,50f,100f)
        var table = Table(columnWidth)
        table.addCell(Cell().add(tableHeader[0]))
        table.addCell(Cell().add(tableHeader[1]))
        table.addCell(Cell().add(tableHeader[2]))

        for (i in 0 until 5)
        {
            for(j in 0 until 3)
            {
                table.addCell(Cell().add(str[i][j]))

            }

        }
        doc.add(table)
        var string1 = "Outside"
        var paragraph = Paragraph(string1).setBold()
        paragraph.marginTop=20f
        paragraph.marginLeft=20f
        paragraph.setFontSize(15f)
        doc.add(paragraph)
        val okWidth = 30f
        val naWidth=30f
        val fixWidth=30f
        val timeWidth=100f
        val productWidth=150f
        val notesWidth=300f
        val columnWidth1 = floatArrayOf(okWidth, naWidth, fixWidth)
        val table2 = Table(columnWidth1)
        val tableHeader1 = arrayOf("Ok", "NA", "Fix")
        val str1 = arrayOf(arrayOf("1", "2","3"),
                           arrayOf("1", "2","3"),
                           arrayOf("1", "2","3"),
                           arrayOf("1", "2","3"),
                           arrayOf("1", "2","3"),
                           arrayOf("1", "2","3"),
                           arrayOf("1", "2","3"),
                           arrayOf("1", "2", "3"),
                           arrayOf("1", "2","3"),
                           arrayOf("1", "2","3"))
        table2.addCell(Cell().add(tableHeader1[0]))
        table2.addCell(Cell().add(tableHeader1[1]))
        table2.addCell(Cell().add(tableHeader1[2]))





        table2.addCell(Cell().add("5"))
        doc.add(table2)
        doc.close()
    }





}