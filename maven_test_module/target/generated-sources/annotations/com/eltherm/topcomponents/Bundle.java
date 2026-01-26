package com.eltherm.topcomponents;
/** Localizable strings for {@link com.eltherm.topcomponents}. */
class Bundle {
    /**
     * @return <i>Navigator</i>
     * @see NavigatorTopComponent
     */
    static String CTL_NavigatorAction() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_NavigatorAction");
    }
    /**
     * @return <i>Navigator Window</i>
     * @see NavigatorTopComponent
     */
    static String CTL_NavigatorTopComponent() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "CTL_NavigatorTopComponent");
    }
    /**
     * @return <i>This is a Navigator window</i>
     * @see NavigatorTopComponent
     */
    static String HINT_NavigatorTopComponent() {
        return org.openide.util.NbBundle.getMessage(Bundle.class, "HINT_NavigatorTopComponent");
    }
    private Bundle() {}
}
