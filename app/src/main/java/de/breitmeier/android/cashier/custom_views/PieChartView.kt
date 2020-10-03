package de.breitmeier.android.cashier.custom_views

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import de.breitmeier.android.cashier.database.productdb.Product
import de.breitmeier.android.cashier.database.toolsdb.Session
import kotlin.math.min

class PieChartView @JvmOverloads constructor(
    context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
        ) : View(context, attrs, defStyleAttr) {

    private var radius = 0.0f

    private lateinit var colorPalette: List<Int>

    private var session: Session? = null
    private var products: List<Product> = listOf()
    private var totalPrice: Int = 0
    private var amount: Int = 0

    private val stroke = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        typeface = Typeface.DEFAULT
        strokeWidth = 4.0f
        color = Color.BLACK
    }

    private val fill = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.FILL
    }

    private val text = Paint(Paint.ANTI_ALIAS_FLAG).apply {
        style = Paint.Style.STROKE
        strokeWidth = 2.0f
        color = Color.BLACK
    }

    init {
        radius = (min(width, height) /2.0 *0.9).toFloat()
    }

    fun setSession(session: Session) {
        this.session = session

        if (session.products != null) {

            // delete products with no revenue
            for (p in this.session!!.products!!) {
                if (p.total <= 0) {
                    this.session!!.products?.remove(p)
                }
            }


            colorPalette = session.products?.size?.let { getColorPalette(it) }!!
            calculatePrice(session.products!!)
            this.products = session.products!!

        }
        // before this.session equals to null don't draw something
        // if session isn't null anymore invalidate and draw everything
        invalidate()
    }

    private fun calculatePrice(products: List<Product>) {
        for (p in products) {
            totalPrice += p.total * p.sessionCunt
        }
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        radius = (min(width, height/2) / 2.0 * 0.9).toFloat()
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if (canvas != null && this.session != null) {

            // draw areas between different products
            drawAreas(canvas)

            // draw at the and to overdraw the other strokes
            drawCircle(canvas)

            // draw list of product with color to identify in circle
            drawProduct(canvas)
        }
    }

    private fun drawAreas(canvas: Canvas) {
        var oldAngle = 0.0
        var newAngle: Double

        for (i in products.indices) {
            val p = products[i]

            // calculate total revenue made with the product
            val totalProduct = p.total * p.sessionCunt

            // change fill color
            changeFillColor(i)

            // calculate angle regarding to percentage of the total amount
            newAngle = oldAngle + Math.PI * totalProduct / totalPrice - Math.PI /2

            // fill space between areas
            fillArea(canvas, oldAngle, newAngle)

            // draw lines between different areas
            drawAreasBorder(canvas, newAngle)

            // now newAngle is the oldAngle
            oldAngle = newAngle
        }
    }

    private fun changeFillColor(i: Int) {
        fill.color = colorPalette[i]
    }

    private fun fillArea(canvas: Canvas, oldAngle: Double, newAngle: Double) {
        // create helper rect
        val oval: RectF = RectF()
        // calculate start and end point and set to oval
        // Point(x|y) = Pint(left|top)
        oval.left = width/2 + (Math.cos(oldAngle) * radius).toFloat()
        oval.top = height/4 + (Math.sin(oldAngle) * radius).toFloat()
        oval.right = width/2 + (Math.cos(newAngle) * radius).toFloat()
        oval.bottom = height/4 + (Math.sin(newAngle) * radius).toFloat()

        canvas.drawArc(oval, oldAngle.toFloat(), newAngle.toFloat(), false, fill)
    }

    private fun drawAreasBorder(canvas: Canvas, angle: Double) {
        // set strokeWidth to 4.0f
        stroke.strokeWidth = 4.0f
        // draw line on canvas
        canvas.drawLine((width / 2).toFloat(), (height / 4).toFloat(),
            (width / 2 + Math.cos(angle) * radius).toFloat(),
            (height / 4 + Math.sin(angle) * radius).toFloat(), stroke)
    }

    private fun drawCircle(canvas: Canvas) {
        stroke.strokeWidth = 4.0f
        canvas.drawCircle((width / 2).toFloat(), (height / 2).toFloat(), radius, stroke)
    }

    private fun drawProduct(canvas: Canvas) {
        // calculate the space for one Object
        // divide available space (height/2) with number of products
        // (and add 3% empty space by multiplying with 0.98)
        val spaceY = height/2.0f / session?.products?.size!!
        val smallCircleRadius = spaceY*0.97f /2

        // calculate width of segments
        val spaceCircleX = spaceY/2.0f
        val spacePercentageX = width * 0.15f
        val spaceNameX = width - spaceCircleX - spacePercentageX

        text.textSize = spaceY * 0.75f

        for (i in products.indices) {
            val p = products[i]
            val percentage = (p.total * p.sessionCunt) / totalPrice * 100.0f

            fill.color = colorPalette[i]

            // there is no length check if text, especially name, is to long for the amount of
            // width pixels
            drawProductColor(canvas, i, spaceY, smallCircleRadius)

            drawProductName(canvas, i, spaceCircleX, spaceY, p.name)

            drawProductPercentage(canvas, i, spaceNameX + spaceCircleX, spaceY, percentage)
        }
    }

    private fun drawProductColor(canvas: Canvas, i: Int, space: Float, r: Float) {
        fill.color = colorPalette[i]
        // cx, cy, radius, paint
        // add space between start of circle and start of view with 1.02
        // calculate the position on screen regarding to position in list of products
        canvas.drawCircle(r*1.02f, height/2 + space/2 + space*i, r, fill)
    }

    private fun drawProductName(canvas: Canvas, i: Int, spacingX: Float,  spaceY: Float, name: String) {
        canvas.drawText(name, spacingX, height/2.0f + spaceY*i - spaceY*0.02f, text)
    }

    private fun drawProductPercentage(canvas: Canvas, i: Int, spacingX: Float, spaceY: Float,
                                      percentage: Float) {

        val s = "%2.f".format(percentage)
        canvas.drawText(s, spacingX, height/2.0f + spaceY*i - spaceY*0.02f, text)
    }

}

























