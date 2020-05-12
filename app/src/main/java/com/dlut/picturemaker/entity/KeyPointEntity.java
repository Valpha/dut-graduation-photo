package com.dlut.picturemaker.entity;

import java.util.List;

public class KeyPointEntity {

    private String logId;
    private List<PersonInfoEntity> personInfo;
    private int personNum;

    public String getLogId() {
        return logId;
    }

    public void setLogId(String logId) {
        this.logId = logId;
    }

    public List<PersonInfoEntity> getPersonInfo() {
        return personInfo;
    }

    public void setPersonInfo(List<PersonInfoEntity> personInfo) {
        this.personInfo = personInfo;
    }

    public int getPersonNum() {
        return personNum;
    }

    public void setPersonNum(int personNum) {
        this.personNum = personNum;
    }

    public static class PersonInfoEntity {
        private LocationEntity location;
        private BodyPartsEntity bodyParts;

        public LocationEntity getLocation() {
            return location;
        }

        public void setLocation(LocationEntity location) {
            this.location = location;
        }

        public BodyPartsEntity getBodyParts() {
            return bodyParts;
        }

        public void setBodyParts(BodyPartsEntity bodyParts) {
            this.bodyParts = bodyParts;
        }
    }

    public static class LocationEntity {
        private double left;
        private double score;
        private double top;
        private double width;
        private double height;

        public double getLeft() {
            return left;
        }

        public void setLeft(double left) {
            this.left = left;
        }

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getTop() {
            return top;
        }

        public void setTop(double top) {
            this.top = top;
        }

        public double getWidth() {
            return width;
        }

        public void setWidth(double width) {
            this.width = width;
        }

        public double getHeight() {
            return height;
        }

        public void setHeight(double height) {
            this.height = height;
        }
    }

    public static class BodyPartsEntity {
        private RightWristEntity rightWrist;
        private RightKneeEntity rightKnee;
        private RightAnkleEntity rightAnkle;
        private RightShoulderEntity rightShoulder;
        private LeftElbowEntity leftElbow;
        private LeftEarEntity leftEar;
        private LeftWristEntity leftWrist;
        private RightHipEntity rightHip;
        private RightEyeEntity rightEye;
        private LeftEyeEntity leftEye;
        private NoseEntity nose;
        private RightEarEntity rightEar;
        private RightElbowEntity rightElbow;
        private LeftMouthCornerEntity leftMouthCorner;
        private LeftAnkleEntity leftAnkle;
        private LeftKneeEntity leftKnee;
        private LeftShoulderEntity leftShoulder;
        private NeckEntity neck;
        private RightMouthCornerEntity rightMouthCorner;
        private TopHeadEntity topHead;
        private LeftHipEntity leftHip;

        public RightWristEntity getRightWrist() {
            return rightWrist;
        }

        public void setRightWrist(RightWristEntity rightWrist) {
            this.rightWrist = rightWrist;
        }

        public RightKneeEntity getRightKnee() {
            return rightKnee;
        }

        public void setRightKnee(RightKneeEntity rightKnee) {
            this.rightKnee = rightKnee;
        }

        public RightAnkleEntity getRightAnkle() {
            return rightAnkle;
        }

        public void setRightAnkle(RightAnkleEntity rightAnkle) {
            this.rightAnkle = rightAnkle;
        }

        public RightShoulderEntity getRightShoulder() {
            return rightShoulder;
        }

        public void setRightShoulder(RightShoulderEntity rightShoulder) {
            this.rightShoulder = rightShoulder;
        }

        public LeftElbowEntity getLeftElbow() {
            return leftElbow;
        }

        public void setLeftElbow(LeftElbowEntity leftElbow) {
            this.leftElbow = leftElbow;
        }

        public LeftEarEntity getLeftEar() {
            return leftEar;
        }

        public void setLeftEar(LeftEarEntity leftEar) {
            this.leftEar = leftEar;
        }

        public LeftWristEntity getLeftWrist() {
            return leftWrist;
        }

        public void setLeftWrist(LeftWristEntity leftWrist) {
            this.leftWrist = leftWrist;
        }

        public RightHipEntity getRightHip() {
            return rightHip;
        }

        public void setRightHip(RightHipEntity rightHip) {
            this.rightHip = rightHip;
        }

        public RightEyeEntity getRightEye() {
            return rightEye;
        }

        public void setRightEye(RightEyeEntity rightEye) {
            this.rightEye = rightEye;
        }

        public LeftEyeEntity getLeftEye() {
            return leftEye;
        }

        public void setLeftEye(LeftEyeEntity leftEye) {
            this.leftEye = leftEye;
        }

        public NoseEntity getNose() {
            return nose;
        }

        public void setNose(NoseEntity nose) {
            this.nose = nose;
        }

        public RightEarEntity getRightEar() {
            return rightEar;
        }

        public void setRightEar(RightEarEntity rightEar) {
            this.rightEar = rightEar;
        }

        public RightElbowEntity getRightElbow() {
            return rightElbow;
        }

        public void setRightElbow(RightElbowEntity rightElbow) {
            this.rightElbow = rightElbow;
        }

        public LeftMouthCornerEntity getLeftMouthCorner() {
            return leftMouthCorner;
        }

        public void setLeftMouthCorner(LeftMouthCornerEntity leftMouthCorner) {
            this.leftMouthCorner = leftMouthCorner;
        }

        public LeftAnkleEntity getLeftAnkle() {
            return leftAnkle;
        }

        public void setLeftAnkle(LeftAnkleEntity leftAnkle) {
            this.leftAnkle = leftAnkle;
        }

        public LeftKneeEntity getLeftKnee() {
            return leftKnee;
        }

        public void setLeftKnee(LeftKneeEntity leftKnee) {
            this.leftKnee = leftKnee;
        }

        public LeftShoulderEntity getLeftShoulder() {
            return leftShoulder;
        }

        public void setLeftShoulder(LeftShoulderEntity leftShoulder) {
            this.leftShoulder = leftShoulder;
        }

        public NeckEntity getNeck() {
            return neck;
        }

        public void setNeck(NeckEntity neck) {
            this.neck = neck;
        }

        public RightMouthCornerEntity getRightMouthCorner() {
            return rightMouthCorner;
        }

        public void setRightMouthCorner(RightMouthCornerEntity rightMouthCorner) {
            this.rightMouthCorner = rightMouthCorner;
        }

        public TopHeadEntity getTopHead() {
            return topHead;
        }

        public void setTopHead(TopHeadEntity topHead) {
            this.topHead = topHead;
        }

        public LeftHipEntity getLeftHip() {
            return leftHip;
        }

        public void setLeftHip(LeftHipEntity leftHip) {
            this.leftHip = leftHip;
        }
    }

    public static class RightWristEntity {
        private double score;
        private double x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class RightKneeEntity {
        private double score;
        private double x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class RightAnkleEntity {
        private double score;
        private double x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class RightShoulderEntity {
        private double score;
        private double x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class LeftElbowEntity {
        private double score;
        private double x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class LeftEarEntity {
        private double score;
        private double x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class LeftWristEntity {
        private double score;
        private double x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class RightHipEntity {
        private double score;
        private double x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class RightEyeEntity {
        private double score;
        private double x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class LeftEyeEntity {
        private double score;
        private double x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class NoseEntity {
        private double score;
        private int x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class RightEarEntity {
        private double score;
        private double x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class RightElbowEntity {
        private double score;
        private double x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class LeftMouthCornerEntity {
        private double score;
        private double x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class LeftAnkleEntity {
        private double score;
        private double x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class LeftKneeEntity {
        private double score;
        private double x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class LeftShoulderEntity {
        private double score;
        private double x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class NeckEntity {
        private double score;
        private int x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class RightMouthCornerEntity {
        private double score;
        private double x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class TopHeadEntity {
        private double score;
        private int x;
        private double y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public int getX() {
            return x;
        }

        public void setX(int x) {
            this.x = x;
        }

        public double getY() {
            return y;
        }

        public void setY(double y) {
            this.y = y;
        }
    }

    public static class LeftHipEntity {
        private double score;
        private double x;
        private int y;

        public double getScore() {
            return score;
        }

        public void setScore(double score) {
            this.score = score;
        }

        public double getX() {
            return x;
        }

        public void setX(double x) {
            this.x = x;
        }

        public int getY() {
            return y;
        }

        public void setY(int y) {
            this.y = y;
        }
    }
}
