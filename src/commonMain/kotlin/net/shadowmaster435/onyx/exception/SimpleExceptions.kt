package net.shadowmaster435.onyx.exception

class IllegalOperationException(msg: String) : Exception(msg)
class UnknownOperationException(msg: String) : Exception(msg)
class VirtualAccessException(msg: String) : Exception(msg)
class NotANumberException(msg: String) : Exception(msg)
class NotABooleanException(msg: String) : Exception(msg)
class UninitializedException(msg: String) : Exception(msg)
class TypeMismatchException(msg: String) : Exception(msg)
class IncorrectParametersException(msg: String) : Exception(msg)
class NonExistenceException(msg: String) : Exception(msg)
class ImpossibleException(msg: String = "This should never happen") : Exception(msg)
