package myana;

import java.util.List;

import org.eclipse.draw2d.FigureCanvas;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LightweightSystem;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.Panel;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Canvas;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.part.ViewPart;

import javassist.CtField;
import javassist.CtMethod;
import myshape.enty.myUmlFigure;
import myshape.enty.adapter.ClassDetail;
import myshape.enty.adapter.Context;

public class UmlView extends ViewPart {
	private static FigureCanvas canvas;
	private static GridLayout gridLayout;
	private GridData layoutData;
	private IFigure contents;
	private Canvas canvas2;
	LightweightSystem system;
	public UmlView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		// �½����ֹ�����
				gridLayout = new GridLayout(5, false);
				layoutData = new GridData(GridData.FILL_BOTH);
				// �½� ���� ���
				canvas2 = new Canvas(parent, SWT.DOUBLE_BUFFERED);

				// Ϊ�����͸���������ò��ֹ�����
				parent.setLayout(gridLayout);
				parent.setLayoutData(layoutData);
				canvas2.setLayoutData(layoutData);
				canvas2.setLayout(gridLayout);

				IFigure contents2 = getContents();

				system = new LightweightSystem(canvas2);
				// system.setContents(contents2);
				// �½�ͼ������
				canvas = new FigureCanvas(canvas2);
				//
				// Ϊͼ���������ò��ֹ���
				canvas.setLayout(gridLayout);
				canvas.setLayoutData(layoutData);

				// canvas.getViewport().setContentsTracksHeight(true);
				// canvas.getViewport().setContentsTracksWidth(true);
				// Ϊͼ�ι�������������
				canvas.setContents(contents2);

	}

	private IFigure getContents() {
		// �½�һ��ͼ�����������Ϊ�������ñ߿�
				contents = new Panel();
				contents.setBorder(new LineBorder());
				// �½���ʽ���ֹ�������Ϊ�������ù�����

				org.eclipse.draw2d.GridLayout Layout = new org.eclipse.draw2d.GridLayout(5, false);

				Layout.horizontalSpacing = 15;
				Layout.marginHeight = 15;
				Layout.marginWidth = 15;
				Layout.verticalSpacing = 15;
			
				addAllClass();
				
				contents.setLayoutManager(Layout);

				return contents;
	}

	private void addAllClass() {
		Context context = new Context();
		
		context.analyse("C:/Users/cobbl/Desktop/�����ļ�/�鼮/Eclipse/org.eclipse.draw2d.examples");
		List<ClassDetail> detail = context.getClassDetail();
		for (ClassDetail classDetail : detail) {
			String className = classDetail.getClassName();
			if(!className.contains("$")){
				myUmlFigure figure = new myUmlFigure();
				figure.setClassName(className);
				List<CtField> attributes = classDetail.getAttributes();
				for (CtField ctField : attributes) {
					figure.appendFieldsName(ctField.getName());
				}
				List<CtMethod> methods = classDetail.getMethods();
				for (CtMethod ctMethod : methods) {
					figure.appendMethodsName(ctMethod.getName());
				}
				contents.add(figure);
			}
		}
		
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}
//====================================================================================
	public static void main(String[] args) {
		UmlView view = new UmlView();
		view.run();
		IFigure iFigure = view.getContents();
		canvas.setContents(iFigure);
	}


	private void run() {
		Display display = Display.getDefault();
		Shell shell = new Shell(display);
		gridLayout = new GridLayout();
		layoutData = new GridData(GridData.FILL_BOTH);
		shell.setLayout(gridLayout);

		
		// �½� ���� ���
		canvas2 = new Canvas(shell, SWT.DOUBLE_BUFFERED);

		// Ϊ�����͸���������ò��ֹ�����
		canvas2.setLayoutData(layoutData);
		canvas2.setLayout(gridLayout);
		
		
		LightweightSystem system2 = new LightweightSystem(canvas2);
		
		
		
		canvas = new FigureCanvas(canvas2, SWT.DOUBLE_BUFFERED|SWT.V_SCROLL);
		
		canvas.setLayoutData(new GridData(GridData.FILL_BOTH));
		canvas.setLayout(gridLayout);

		shell.open();
		while (!shell.isDisposed()) {
			while (!display.readAndDispatch()) {
				display.sleep();
			}
		}

	}
}