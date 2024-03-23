package rngGame.main

private const val VARIABLE_THETA_ANGLE: Double = 0.2
private const val VARIABLE_THETA_NEXT_ANGLE: Double = 0.3927

//0.3927 1.1781 1.9635 2.7489
enum class Direction(val minAngle: Double, val maxAngle: Double) {
	N(-1.9635, -1.1781),
	NE(-1.1781, -0.3927),
	E(-0.3927, 0.3927),
	SE(0.3927, 1.1781),
	S(1.1781, 1.9635),
	SW(1.9635, 2.7489),
	W(2.7489, -2.7489),
	NW(-2.7489, -1.9635);

	private var _active: Boolean = false

	var active
		get() = _active
		set(value) {
			entries.forEach {
				it._active = false
			}
			_active = value
		}

	companion object {
		fun getDirectionFromAngle(angle: Double) = entries.firstOrNull {
				if (it.minAngle > it.maxAngle)
					angle > it.minAngle || angle < it.maxAngle
				else
					angle > it.minAngle && angle < it.maxAngle
			} ?: E
	}
}