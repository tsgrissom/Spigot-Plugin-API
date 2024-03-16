package io.github.tsgrissom.pluginapi.extension.bukkit

import org.bukkit.attribute.Attribute
import org.bukkit.entity.Damageable
import org.bukkit.entity.LivingEntity

fun normalizeHealthPercentage(percentage: Double) : Double {
    return if (percentage > 1.0) 1.0 else if (percentage < 0.0) 0.0 else percentage
}

fun Damageable.setHealthByPercentage(d: Double) {
    val value = normalizeHealthPercentage(d) * this.maxHealth
    this.health = value
}

fun Damageable.damageByPercentage(d: Double) {
    val value = normalizeHealthPercentage(d) * this.maxHealth
    this.damage(value)
}

fun Damageable.healByPercentage(d: Double) {
    val value = normalizeHealthPercentage(d) * this.maxHealth
    this.health += value
}

fun LivingEntity.getMaxHealth(defaultIfNull: Double = 20.0) : Double {
    val attr = this.getAttribute(Attribute.GENERIC_MAX_HEALTH) ?: return defaultIfNull
    return attr.value
}

/**
 * Get the default max health of the LivingEntity.
 * Otherwise fall back on the defaultIfNull (default: 20.0).
 * @param defaultIfNull The fallback value to use if the GENERIC_MAX_HEALTH attribute is null.
 * @return The default max health value of the LivingEntity.
 */
fun LivingEntity.getDefaultMaxHealth(defaultIfNull: Double = 20.0) : Double {
    val attr = this.getAttribute(Attribute.GENERIC_MAX_HEALTH) ?: return defaultIfNull
    return attr.defaultValue
}

fun LivingEntity.addMaxHealth(d: Double, unsafe: Boolean = false) {
    if (unsafe) {
        this.maxHealth += d
    } else {
        val futureMax = this.maxHealth + d
        val defaultMax = this.getDefaultMaxHealth()

        if (futureMax < defaultMax) {
            this.maxHealth = defaultMax
        } else {
            this.maxHealth = futureMax
        }
    }
}

fun LivingEntity.subtractMaxHealth(d: Double, unsafe: Boolean = false) {
    if (unsafe) { // Safe mode off, immediately set value
        this.maxHealth -= d
    } else { // Safe mode on, calculate future max and check if less than default max health
        val futureMax = this.maxHealth - d
        val defaultMax = this.getDefaultMaxHealth()

        if (futureMax < defaultMax) { // Unsafe, set to default
            this.maxHealth = defaultMax
        } else { // Safely proceed
            this.maxHealth = futureMax
        }
    }
}

fun LivingEntity.multiplyMaxHealth(d: Double, unsafe: Boolean = false) {
    if (unsafe) {
        this.maxHealth *= d
    } else {
        val futureMax = this.maxHealth * d
        val defaultMax = this.getDefaultMaxHealth()

        if (futureMax < defaultMax) {
            this.maxHealth = defaultMax
        } else {
            this.maxHealth = futureMax
        }
    }
}

fun LivingEntity.divideMaxHealth(d: Double, unsafe: Boolean = false) {
    if (unsafe) {
        this.maxHealth /= d
    } else {
        val futureMax = this.maxHealth / d
        val defaultMax = this.getDefaultMaxHealth()

        if (futureMax < defaultMax) {
            this.maxHealth = defaultMax
        } else {
            this.maxHealth = futureMax
        }
    }
}