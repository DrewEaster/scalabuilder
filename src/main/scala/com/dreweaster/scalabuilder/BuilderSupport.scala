package utils

trait Buildable[T] {
  type Result

  def newBuilder: Result
}

trait Builder[T] {
  def get(): T
}

trait BuilderSupport {
  implicit def builderToBuilt[T](builder: Builder[T]): T = builder.get()

  def build[T](implicit B: Buildable[T]): B.Result = B.newBuilder
}
