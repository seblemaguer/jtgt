package org.m2ci.msp.jtgt;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * A helper class to represent a tier in the textgrid
 *
 * @author <a href="mailto:slemaguer@coli.uni-saarland.de">Sébastien Le Maguer</a>
 */
public abstract class Tier {
    /** List of annotations */
    private ArrayList<Annotation> annotations;

    /** Start of the tier */
    private double start;

    /** End of the tier */
    private double end;

    /** Name of the tier */
    private String name;


    /*********************************************************************************************
     ** Constructors
     *********************************************************************************************/

    /**
     * Constructor of an empty tier
     *
     * @param name name of the tier
     */
    protected Tier(String name) {
        setStart(-1);
        setEnd(-1);
        setName(name);
        setAnnotations(new ArrayList<Annotation>());
    }

    /**
     * Constructor of pre-filled tier
     *
     * @param name the name of the tier
     * @param start the start time of the tier
     * @param end the end time of the tier
     * @param annotations the list of annotations
     */
    protected Tier(String name, double start, double end, ArrayList<Annotation> annotations) {
        setName(name);
        setStart(start);
        setEnd(end);
        setAnnotations(annotations);
    }

    /*********************************************************************************************
     ** Accessors
     *********************************************************************************************/

    /**
     * Get the start time of the tier
     *
     * @return the start time of the tier
     */
    public double getStart() {
        return start;
    }

    /**
     * Get the end time of the tier
     *
     * @return the end time of the tier
     */
    public double getEnd() {
        return end;
    }

    /**
     * Get the name of the tier
     *
     * @return the name of the tier
     */
    public String getName() {
        return name;
    }

    /**
     * Get the list of annotations composing the tier
     *
     * @return the list of annotations composing the tier
     */
    public ArrayList<Annotation> getAnnotations() {
        return annotations;
    }

    /**
     * Get the list of annotations which are in a specific interval
     *
     * @param start the beginning of the interval
     * @param end the end of the interval
     * @return the list of annotations which are in the described interval
     */
    public ArrayList<Annotation> getAnnotationsBetween(double start, double end) {
        ArrayList<Annotation> annotations = new ArrayList<Annotation>();
        for (Annotation an: getAnnotations()) {

            // We passed the end => get away
            if (an.getEnd() > end) {
                break;
            }

            // Add the annotation if start is passed
            if (an.getStart() >= start) {
                annotations.add(an);
            }
        }

        return annotations;
    }

    /**
     * Set the start time of the tier
     *
     * @param start the start time of the tier
     */
    public void setStart(double start) {
        this.start = start;
    }

    /**
     * Set the end time of the tier
     *
     * @param end the end time of the tier
     */
    public void setEnd(double end) {
        this.end = end;
    }

    /**
     * Set the name of the tier
     *
     * @param name the name of the tier
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Set the annotations composing the tier
     *
     * @param annotations the list of annotations composing the tiers
     */
    public void setAnnotations(ArrayList<Annotation> annotations) {
        this.annotations = annotations;
    }

    /**
     * Add an annotation at the end of the annotation list composing the tier
     *
     * @param annotation the annotation to add
     */
    public void addAnnotation(Annotation annotation) {
        annotations.add(annotation);


        // Adapt the end time if adding the annotation implies a change
        if (getEnd() < annotation.getEnd()) {
            setEnd(annotation.getEnd());
        }
    }

    /**
     * Add a list of annotations at the end of the annotation list composing the tier
     *
     * @param annotations the list of annotations to add
     */
    public void addAnnotations(ArrayList<Annotation> annotations) {
        Annotation end_it = null;
        for (Annotation an : annotations) {
            end_it = an;
            this.annotations.add(an);
        }

        // Adapt the end time if adding the annotations implies a change
        if ((end_it != null) && (getEnd() < end_it.getEnd())) {
            setEnd(end_it.getEnd());
        }
    }


    /**
     * Check if a given object is equal to the current tier
     *
     * @param o the given object
     * @return true if equality, false else
     */
    @Override
    public boolean equals(Object o) {
        if (o == null) {
            return false;
        }

        if (!(o instanceof Tier)) {
            return false;
        }

        return ((getStart() == ((Tier) o).getStart()) &&
                (getEnd() == ((Tier) o).getEnd())  &&
                getName().equals(((Tier) o).getName()) &&
                getAnnotations().equals(((Tier) o).getAnnotations()));
    }

    public String toString() {
        String jsonStr = new ReflectionToStringBuilder(this, ToStringStyle.JSON_STYLE).toString();
        return new JSONObject(jsonStr).toString(4);
    }
}

/* Tier.java ends here */
