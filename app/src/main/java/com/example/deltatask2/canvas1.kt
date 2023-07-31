package com.example.deltatask2

import android.app.Dialog
import android.content.Context
import android.graphics.*
import android.view.MotionEvent
import android.view.View
import kotlin.math.roundToInt

class canvas1(context: Context?,val dialog:()->Unit) : View(context) {

    private lateinit var rect: Rect
    private var paint: Paint
    private var circleX = 400f
    private var circleY = 800f
    private var velocity = 0f
    private var gravity = 0.3f
    private val radius = 100f
    private var numTimesHitSmallObstacle = 0
    private var numTimesHitLargeObstacle = 0
    private var isHittingTop = false
    private val initialGravity = 0.3f
    private val increasedGravity = 1.5f
    var a : Int = 0
    var b : Int = 0
    var score:Int = 0
    private var highScore = 0
    private var isJumping = false
    private var isJumpingAllowed = true





    private val bheem by lazy {
        val originalBitmap = BitmapFactory.decodeResource(resources, R.drawable.bheem1)
        val scaledBitmap = Bitmap.createScaledBitmap(originalBitmap, radius.toInt() * 2, radius.toInt() * 2, false)
        originalBitmap.recycle()
        scaledBitmap
    }
    private val obstacleBitmap: Bitmap by lazy {
        BitmapFactory.decodeResource(resources, R.drawable.tree)
    }
    private val obstacleBitmapList: MutableList<Bitmap> = mutableListOf()


    // Define obstacle variables
    private val obstacleList: MutableList<Rect> = mutableListOf()
    private val obstaclePaint = Paint()
    private var obstacleSpeed = 15f
    private enum class ObstacleType(val heightRatio: Float, val spacingRatio: Float) {
        LARGE(0.7f, 0.7f),
        SMALL(0.4f, 0.7f)
    }

    private var isTouched = false

    init {
        paint = Paint()
        paint.color = Color.BLUE
        paint.textSize = 56F

        obstaclePaint.color = Color.RED

        // Create initial obstacles

        while (a <10) {
            // if(a==9){
            //    a=0
            // }

            a++
            var i=a

            val obstacleType = if (i % 2 == 0) ObstacleType.LARGE else ObstacleType.SMALL

            val obstacleHeight = (height * obstacleType.heightRatio).toInt()
            val obstacleWidth = (obstacleHeight * 0.5).toInt()
            val spacing = width * obstacleType.spacingRatio
            val obstacleRect = Rect(
                (width + (i * spacing)).toInt(),
                height - obstacleHeight,
                (width + obstacleWidth + (i * spacing)).toInt(),
                height
            )

            obstacleList.add(obstacleRect)


        }

    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)



        setBackgroundResource(R.drawable.dhola)


        // Draw the circle
        canvas?.drawBitmap(bheem, circleX - radius, circleY - radius, paint)

        // Draw obstacles (rectangles)
        canvas?.drawText(score.toString(), 50F, 50F, paint)

        var c  = 0

        if(obstacleList.isEmpty())
        {
            dialog()
            return

        }
        score += 1



        // Draw each obstacle in the list
        val obstaclesToRemove = mutableListOf<Rect>()
        for (obstacle in obstacleList) {
            canvas?.drawBitmap(obstacleBitmap, null, obstacle, obstaclePaint)


            // Move the obstacle towards the left
            val newLeft = obstacle.left - obstacleSpeed
            val newRight = obstacle.right - obstacleSpeed
            val newObstacle = Rect(
                newLeft.roundToInt(), obstacle.top,
                newRight.roundToInt(), obstacle.bottom
            )

            if (newRight > 0) {
                obstacleList[obstacleList.indexOf(obstacle)] = newObstacle
            } else {
                // Remove the obstacle if it has reached the left edge of the screen
                obstaclesToRemove.add(obstacle)
            }


        }




        // Remove obstacles that have reached the left edge
        obstacleList.removeAll(obstaclesToRemove)

        // Update the circle's position and velocity
        if (isJumping) {
            // Move the player upwards
            velocity -= 2f
        } else {
            // Apply gravity to make the player fall down
            velocity += gravity
        }
        circleY += velocity

        // Check if the player exceeds the screen boundaries
        if (circleY + radius > height) {
            // If the player reaches the bottom of the screen, reverse the velocity to make it bounce
            velocity = -velocity * 0.6f
            circleY = height - radius
        } else if (circleY - radius < 0) {
            // If the player reaches the top of the screen, reset the velocity and toggle jumping
            velocity = 5f
            isJumping = false
            isJumpingAllowed = true
        }

        checkCollisionAndResetGame()

        // Request a redraw
        invalidate()
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        if (event?.action == MotionEvent.ACTION_DOWN) {
            if (!isJumping) {
                // Screen is pressed and the player is not currently jumping
                isJumping = true
            }
        }
        return super.onTouchEvent(event)
    }



    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)

        // Create initial obstacles
        obstacleList.clear()
        while (b < 10) {
            b += 1
            var i = b
            val obstacleType = if (i % 2 == 0) ObstacleType.LARGE else ObstacleType.SMALL
            val obstacleHeight = (h * obstacleType.heightRatio).toInt()
            val obstacleWidth = (obstacleHeight * 0.5).toInt()

            val spacing = w * obstacleType.spacingRatio
            val obstacleRect = Rect(
                (w + (i * spacing)).toInt(),
                h - obstacleHeight,
                (w + obstacleWidth + (i * spacing)).toInt(),
                h
            )

            obstacleList.add(obstacleRect)

            // Load scaled tree image for each obstacle
            val scaledObstacleBitmap = Bitmap.createScaledBitmap(
                obstacleBitmap,
                obstacleRect.width(),
                obstacleRect.height(),
                false
            )
            obstacleBitmapList.add(scaledObstacleBitmap)
        }
    }



    fun resetGame() {
        // Reset circle position and velocity
        circleX = 400f
        circleY = 800f
        velocity = 0f

        // Reset obstacle list
        obstacleList.clear()

        // Create initial obstacles
        for (i in 1..10) {
            val obstacleType = if (i % 2 == 0) ObstacleType.LARGE else ObstacleType.SMALL
            val obstacleHeight = (height * obstacleType.heightRatio).toInt()
            val obstacleWidth = (obstacleHeight * 0.5).toInt()

            val spacing = width * obstacleType.spacingRatio
            val obstacleRect = Rect(
                (width + (i * spacing)).toInt(),
                height - obstacleHeight,
                (width + obstacleWidth + (i * spacing)).toInt(),
                height
            )

            obstacleList.add(obstacleRect)

        }

        // Reset other game variables
        numTimesHitSmallObstacle = 0
        numTimesHitLargeObstacle = 0
        score = 0
        highScore = 0


        // Start the game by calling onDraw
        invalidate()
    }

    private fun handleCollisionWithSmallObstacle() {
        if (numTimesHitSmallObstacle == 0) {
            // First collision with small obstacle
            numTimesHitSmallObstacle++
            obstacleSpeed = 9f

            // Move the obstacle off the screen to allow the player to pass through
            obstacleList[0].offsetTo(-obstacleList[0].width(), obstacleList[0].top)
        } else if (numTimesHitSmallObstacle == 1) {
            // Second collision with small obstacle
            dialog()
            background()

        }
    }

    private fun checkCollisionAndResetGame() {
        val circleRectF = RectF(
            circleX - radius,
            circleY - radius,
            circleX + radius,
            circleY + radius
        )

        val obstaclesToRemove = mutableListOf<Rect>()

        for (obstacle in obstacleList) {
            val obstacleRectF = RectF(obstacle)
            if (RectF.intersects(obstacleRectF, circleRectF)) {
                if (obstacle.height() > (height * ObstacleType.SMALL.heightRatio).toInt()) {
                    // Circle collided with a large obstacle
                    dialog()
                    background()
                    return
                } else {
                    // Circle collided with a small obstacle
                    obstaclesToRemove.add(obstacle)
                }
            }
        }

        obstacleList.removeAll(obstaclesToRemove)

        if (obstaclesToRemove.isNotEmpty()) {
            handleCollisionWithSmallObstacle()
        }
    }

    fun background()
    {
        obstacleList.clear()
    }






}