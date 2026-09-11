// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

import static edu.wpi.first.units.Units.Volts;

import com.ctre.phoenix6.hardware.TalonFX;

import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.RobotController;
import edu.wpi.first.wpilibj.simulation.DCMotorSim;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class ExampleSubsystem extends SubsystemBase {
    private final TalonFX motor;

    private final DCMotorSim motorSim = new DCMotorSim(
        LinearSystemId.createDCMotorSystem(
            DCMotor.getKrakenX60Foc(1),
            0.001,
            1.0
        ),
        DCMotor.getKrakenX60Foc(1)
    );

    public ExampleSubsystem() {
        motor = new TalonFX(15);
    }

    // Students: add your motor command here.

    @Override
    public void periodic() {}

    @Override
    public void simulationPeriodic() {
        var motorSimState = motor.getSimState();

        motorSimState.setSupplyVoltage(RobotController.getBatteryVoltage());

        var motorVoltage = motorSimState.getMotorVoltageMeasure();
        motorSim.setInputVoltage(motorVoltage.in(Volts));
        motorSim.update(0.020);

        motorSimState.setRawRotorPosition(motorSim.getAngularPosition());
        motorSimState.setRotorVelocity(motorSim.getAngularVelocity());
    }
}