-keep class io.objectbox.** { *; }
-keep class io.objectbox.exception.** { *; }
-keep class com.ai.assistance.operit.** { *; }

-keepattributes *Annotation*
-keepattributes Signature
-keepattributes InnerClasses

-keepnames class * implements java.io.Serializable
-keepclassmembers class * implements java.io.Serializable {
    static final long serialVersionUID;
    private static final java.io.ObjectStreamField[] serialPersistentFields;
    private void writeObject(java.io.ObjectOutputStream);
    private void readObject(java.io.ObjectInputStream);
    java.lang.Object writeReplace();
    java.lang.Object readResolve();
}

-dontwarn io.objectbox.**
-dontwarn javax.annotation.**
-dontwarn org.jetbrains.annotations.**

-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}

-keep,allowobfuscation,allowshrinking class com.google.gson.reflect.TypeToken
-keep,allowobfuscation,allowshrinking class * extends com.google.gson.reflect.TypeToken

-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
}